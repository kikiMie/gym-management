package com.gym.gymmanagementsystem.service;


public interface ChatService {

    /**
     * 查询 AI 回复（非流式）
     *
     * @param content 用户输入
     * @param model   DeepSeek 模型，允许为空（为空则使用默认模型）
     * @return AI 回复文本
     */
    String queryChat(String content, String model);
}

