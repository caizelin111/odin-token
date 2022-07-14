package com.czl.constants;

/**
 * @author CaiZelin
 * @date 2022/7/14 16:46
 */
public enum OdinConfig {
    TOKEN_HOUSE_ANALLYZE("token-12345678910", "houseAnalyze", "管理后台楼盘智能分析"),
    ;

    private Object defaultValue;
    private String key;
    private String desc;

    private OdinConfig(Object defaultValue, String key, String desc) {
        this.defaultValue = defaultValue;
        this.key = key;
        this.desc = desc;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getKey() {
        return key;
    }
}
