package com.gym.gymmanagementsystem.service.impl;

import com.gym.gymmanagementsystem.service.ChatService;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.springframework.beans.factory.annotation.Value; // 这一行也很重要


    @Service
    public class ChatServiceImpl implements ChatService {
//配置文件里读取 API Key（相当于你的账号密码）和 AI 的地址
        @Value("${deepseek.api.key}")
        private String apiKeyFromConfig;

        @Value("${deepseek.api.url:https://api.deepseek.com/v1/chat/completions}")
        private String apiUrl;

        @Value("${deepseek.model:deepseek-chat}")
        private String defaultModel;

//  检查输入 看看用户发了什么内容。如果没发内容，就提示一下。 确保有 API Key，如果没有就报错，防止程序白跑。
        @Override
        public String queryChat(String content, String model) {
            String userContent = content == null ? "" : content.trim();
            if (userContent.isEmpty()) {
                return "我还没收到你的问题，可以再发一次吗？";
            }

            String resolvedModel = (model == null || model.trim().isEmpty()) ? defaultModel : model.trim();
            String apiKey = apiKeyFromConfig;

            if (apiKey == null || apiKey.trim().isEmpty()) {
                apiKey = System.getenv("DEEPSEEK_API_KEY");
            }

            if (apiKey == null || apiKey.trim().isEmpty()) {
                throw new IllegalStateException("未配置 DeepSeek API Key");
            }
//打包数据
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                Map<String, Object> payload = new HashMap<>();
                payload.put("model", resolvedModel);
                payload.put("stream", false);

                List<Map<String, String>> messages = new ArrayList<>();
                messages.add(new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", "你是一个健身房训练与饮食建议助手。回答要具体、可执行，并给出需要的注意事项。"
                            + "如果用户消息中包含“系统补充信息”里列出的会员课程，请先用简短的中文总结出“该会员报名了哪些课程”，"
                            + "然后再结合这些课程安排来回答用户的后续问题。");
                }});
//                放入问题: 把用户发的话（userContent）放进去。
                messages.add(new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", userContent);
                }});
                payload.put("messages", messages);
//转成json格式
                String requestBody = objectMapper.writeValueAsString(payload);

                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setRequestProperty("Authorization", "Bearer " + apiKey);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

                OutputStream os = connection.getOutputStream();
                os.write(requestBody.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();

                int statusCode = connection.getResponseCode();
                InputStream is = statusCode >= 200 && statusCode < 300 ? connection.getInputStream() : connection.getErrorStream();
                String responseBody = readAll(is);

                if (statusCode < 200 || statusCode >= 300) {
                    throw new RuntimeException("DeepSeek API 请求失败，status=" + statusCode + ", body=" + responseBody);
                }

                JsonNode root = objectMapper.readTree(responseBody);
                JsonNode choices = root.path("choices");
                if (choices.isArray() && choices.size() > 0) {
                    JsonNode message = choices.get(0).path("message");
                    String reply = message.path("content").asText(null);
                    if (reply != null && !reply.trim().isEmpty()) {
                        return reply;
                    }
                }

                throw new RuntimeException("DeepSeek 返回无法解析：choices[0].message.content 为空");
            } catch (IOException e) {
                throw new RuntimeException("DeepSeek 调用失败：" + e.getMessage(), e);
            }
        }

        private String readAll(InputStream is) throws IOException {
            if (is == null) return "";
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        }
    }