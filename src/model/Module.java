package model;

import java.io.Serializable;
import java.util.Objects;


public class Module implements Comparable<Module>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9057586481666222347L;
	private String moduleCode;
	private String moduleName;
	private int credits;
	private boolean mandatory;
	private Delivery runPlan;
	
	public Module(String moduleCode, String moduleName, int credits, boolean mandatory, Delivery runPlan) {
		this.moduleCode = moduleCode;
		this.moduleName = moduleName;
		this.credits = credits;
		this.mandatory = mandatory;
		this.runPlan = runPlan;
	}

	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getCredits() {
		return credits;
	}
	
	public void setCredits(int credits) {
		this.credits = credits;
	}

	public boolean isMandatory() {
		return mandatory;
	}
	
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public Delivery getRunPlan() {
		return runPlan;
	}

	public void setRunPlan(Delivery runPlan) {
		this.runPlan = runPlan;
	}
	
	
	@Override
	public String toString() {
		//a non-standard toString that simply returns the module code and name,
		//so as to assist in displaying modules correctly in a ListView<Module> in the view
		//-Note- you may customise this if you wish to do so.
		return moduleCode + " : " + moduleName;
	}
	
	public String actualToString() {
		return "Module:[moduleCode=" + moduleCode + ", moduleName=" + moduleName + 
				", credits=" + credits + ", mandatory=" + mandatory + ", runPlan=" + runPlan + "]";
	}
	
	@Override
	public int compareTo(Module other) {
		int result = this.moduleCode.compareTo(other.moduleCode);
		
		if (result == 0) {
			result = Integer.compare(this.credits, other.credits);
			
			if (result == 0) {
				result = Boolean.compare(other.mandatory, this.mandatory);
				
				if (result == 0) {
					result = this.moduleName.compareTo(other.moduleName);
					
					if (result == 0) {
						result = this.runPlan.compareTo(other.runPlan);
					}
				}
				
			}
		}
		
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		
		Module other = (Module) obj;
		
		return this.mandatory == other.mandatory && this.credits == other.credits &&
				this.moduleCode.equals(other.moduleCode) && this.moduleName.equals(other.moduleName) &&
				this.runPlan.equals(other.runPlan);
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(mandatory, credits, moduleCode, moduleName, runPlan);
	}
	
	
}
