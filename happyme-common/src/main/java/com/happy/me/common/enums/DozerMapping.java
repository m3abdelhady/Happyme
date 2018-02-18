package com.happy.me.common.enums;

public enum DozerMapping {
    USER_VS_USERDTO("UserVsUserDto"),
    MERCHANTDATA_VS_MERCHANTDTO("MerchantDataVsMerchantDto"),
    MERCHANT_VS_MERCHANTDTO("MerchantVsMerchantDto"),
    MERCHANTRULE_VS_MERCHANTRULEDTO("MerchantRuleVsMerchantRuleDto"),
    APPFEEDBACK_VS_APPFEEDBACKDTO("AppFeedbackVsAppFeedbacKDto"),
    INFO_USER_VS_USERDTO("Info_UserVsUserDto"), 
    FEEDBACK_VS_FEEDBACKDTO("FeedbackVsFeedbackdto"),
    COUPONS_VS_COUPONSDTO("CouponsVsCouponsdto"),
    USERCARD_VS_USERCARDDTO("UserCardVsUserCardDto"),
    CARDSUMMARY_VS_CARDSUMMARYDTO("CardSummaryVSCardSummaryDto"),
    LIGHT_MERCHANT_VS_MERCHANTDTO("Light_MerchantVsMerchantDto"),
    BILLHEADER_VS_BILLHEADERDTO("BillHeaderVsBillHeaderDto"), 
    CREDITDEBIT_VS_CREDITDEBITDTO("CreditDebitVsCreditDebitDto"), 

    

    ;

    private String key;

    private DozerMapping(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}