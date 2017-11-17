package com.happy.me.common.enums;

public enum DozerMapping {
    USER_VS_USERDTO("UserVsUserDto"),
    MERCHANTDATA_VS_MERCHANTDTO("MerchantDataVsMerchantDto"),
    MERCHANT_VS_MERCHANTDTO("MerchantVsMerchantDto"),
    MERCHANTRULE_VS_MERCHANTRULEDTO("MerchantRuleVsMerchantRuleDto"),
    APPFEEDBACK_VS_APPFEEDBACKDTO("AppFeedbackVsAppFeedbacKDto"),
    INFO_USER_VS_USERDTO("Info_UserVsUserDto"), 
    FEEDBACK_VS_FEEDBACKDTO("FeedbackVsFeedbackdto")

    ;

    private String key;

    private DozerMapping(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}