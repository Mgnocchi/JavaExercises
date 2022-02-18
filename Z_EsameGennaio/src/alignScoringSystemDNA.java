import java.util.*;

public class alignScoringSystemDNA{
	public Double match = 1.0d;
	public Double mismatch = - 1.0d/3.0;
	public Double gap = -4.0d/3.0;
	public Map<String,Double> compare = new HashMap<String,Double>();

	alignScoringSystemDNA(){
		String[] nts = {"A","T","C","G"};
		for(String s1 : nts){
			for(String s2 : nts){
				if(s1.equals(s2)){
					this.compare.put(s1+s2, this.match);
				}else{
					this.compare.put(s1+s2, this.mismatch);
				}
			}
		}
		// handle special character 0 derived corner cases (for SW implementation)
		// could be removed with a different approach in index use for SW
		this.compare.put("00", -100.0d);
		this.compare.put("0A", -100.0d);
		this.compare.put("0T", -100.0d);
		this.compare.put("0C", -100.0d);
		this.compare.put("0G", -100.0d);
		this.compare.put("A0", -100.0d);
		this.compare.put("T0", -100.0d);
		this.compare.put("C0", -100.0d);
		this.compare.put("G0", -100.0d);
		
	}

	public void setMatchScore(double m){
		this.match = m;
	}

	public void setMismatchScore(double m){
		this.mismatch = m;
	}

	public void setGapScore(double g){
		this.gap = g;
	}

}
