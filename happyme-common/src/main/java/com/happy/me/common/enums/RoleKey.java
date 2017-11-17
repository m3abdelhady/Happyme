package com.happy.me.common.enums;

public enum RoleKey {

	ADMIN("ADMIN", "Admin"),
	RESELLER("RESELLER", "Reseller"), 
	MANAGER("MANAGER", "Store Manager"), 
	AGENT("AGENT", "Store Agent"),
	USER("USER", "User");

    private String value;
    private String displayName;

    private RoleKey(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return this.value;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public static boolean contains(String value){
    	for(RoleKey key : RoleKey.values()){
    		if(key.getValue().equals(value)){
    			return true;
    		}
    	}
		return false;
    }
    
    public static RoleKey getEnum(String value) {
        for(RoleKey v : values())
            if(v.getValue().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
    
}