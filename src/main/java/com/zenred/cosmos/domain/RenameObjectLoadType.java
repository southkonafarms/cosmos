package com.zenred.cosmos.domain;

public enum RenameObjectLoadType {
	
	RENAMEOBJECTTYPE ("RenameObjectLoadType"){
		@Override
		public String getName() {
			return "renameObjectType";
		}

		@Override
		public void storeValue(String value) {
			String renameObjectType = value;
			
		}
	},
	GENERICNAME ("genericName"){
		@Override
		public String getName() {
			return "genericName";
		}

		@Override
		public void storeValue(String value) {
			String genericName = value;
			
		}
	},
	RENAMENAME ("renameName"){

		@Override
		public String getName() {
			return "renameName";
		}

		@Override
		public void storeValue(String value) {
			String renameName = value;
		}
	},
	RENAMECOUNT ("renameCount"){

		@Override
		public String getName() {
			return "renameCount";
		}

		@Override
		public void storeValue(String value) {
			Integer renameCount = new Integer(value);
			
		}
	}
	;
	
	private String type;
	private RenameObjectLoadType (String type){
		
	}
	
	public abstract String getName();
	public abstract void storeValue(String value);

}

