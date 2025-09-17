package gov.utleg.bigboard.model;

public class BillItem {

	private String sessionID;
	private String fileNumber;
	private String billNumber;
	private String realBillNumber;
	private String sponsorChamber;
	private String primarySponsor;
	private String leadershipPosition;
	private String sponsorID;
	private String floorSponsor;
	private String flLeadershipPosition;
	private String floorSponsorID;
	private String attorney;
	private String lrgcAnalyst;
	private String lfaAnalyst;
	private String owner;
	private String ownerDesc;
	private String shortTitle;
	private String longTitle;
	private Float onetime;
	private Float ongoing;
	private String fiscalBill;
	private String fiscalImpact;
	private Float fiscalTotal;
	private String actionDesc;
	private String actionCode;
	private String actionCodeDesc;
	private String lastActionDate;
	private String impact;
	private String effectiveDate;
	private Integer isPublic;
	private String link;
	private String json;
	private String newlyOwnedBy;
	private String newlyOwnedAction;
	
	private String HRead1;
	private String HRead2;
	private String HRead3;
	private String HPass3;
	private Integer H3Sub;
	private Integer H3Amd;
	private String HPassFinal;
	private String HPassAction;
	private String HComCode;
	private String HComAction;
	private String HComActionDate;
	private Integer HCASub;
	private Integer HCAAmd;
	private String HCRDate;
	private Integer HCRSub;
	private Integer HCRAmd;
	private String SRead1;
	private String SRead2;
	private String SRead3;
	private String SPass2;
	private Integer S2Sub;
	private Integer S2Amd;
	private String SPass3;
	private Integer S3Sub;
	private Integer S3Amd;
	private String SPassFinal;
	private String SPassAction;
	private String SComCode;
	private String SComAction;
	private String SComActionDate;
	private Integer SCASub;
	private Integer SCAAmd;
	private String SCRDate;
	private Integer SCRSub;
	private Integer SCRAmd;
	private String Concur;
	private String PassDate;
	private String GovAction;
	private String GovDate;
	private String OverRide;
	
	private String sectionsAffected;
	private String subjectList;
	private String monies;
	private String provisions;
	private String clauses;
	
	private String failedOnFloorDate;
	private String failedOnFloorAction;
	
	/**
	 * NOTE: this object has special getters for monies and provisions !!!
	 */

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public String getRealBillNumber() {
		return realBillNumber;
	}

	public void setRealBillNumber(String realBillNumber) {
		this.realBillNumber = realBillNumber;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getLongTitle() {
		return longTitle;
	}

	public void setLongTitle(String longTitle) {
		this.longTitle = longTitle;
	}

	public String getPrimarySponsor() {
		return primarySponsor;
	}

	public void setPrimarySponsor(String primarySponsor) {
		this.primarySponsor = primarySponsor;
	}

	public String getFloorSponsor() {
		return floorSponsor;
	}

	public void setFloorSponsor(String floorSponsor) {
		this.floorSponsor = floorSponsor;
	}

	public String getAttorney() {
		return attorney;
	}

	public void setAttorney(String attorney) {
		this.attorney = attorney;
	}

	public String getLrgcAnalyst() {
		return lrgcAnalyst;
	}

	public void setLrgcAnalyst(String lrgcAnalyst) {
		this.lrgcAnalyst = lrgcAnalyst;
	}

	public String getLfaAnalyst() {
		return lfaAnalyst;
	}

	public void setLfaAnalyst(String lfaAnalyst) {
		this.lfaAnalyst = lfaAnalyst;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionCodeDesc() {
		return actionCodeDesc;
	}

	public void setActionCodeDesc(String actionCodeDesc) {
		this.actionCodeDesc = actionCodeDesc;
	}

	public String getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(String lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	public String getImpact() {
		return impact;
	}

	public void setImpact(String impact) {
		this.impact = impact;
	}
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getSponsorID() {
		return sponsorID;
	}

	public void setSponsorID(String sponsorID) {
		this.sponsorID = sponsorID;
	}

	public String getFloorSponsorID() {
		return floorSponsorID;
	}

	public void setFloorSponsorID(String floorSponsorID) {
		this.floorSponsorID = floorSponsorID;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Float getOnetime() {
		return onetime;
	}

	public void setOnetime(Float onetime) {
		this.onetime = onetime;
	}

	public Float getOngoing() {
		return ongoing;
	}

	public void setOngoing(Float ongoing) {
		this.ongoing = ongoing;
	}

	public String getFiscalBill() {
		return fiscalBill;
	}

	public void setFiscalBill(String fiscalBill) {
		this.fiscalBill = fiscalBill;
	}

	public String getFiscalImpact() {
		return fiscalImpact;
	}

	public void setFiscalImpact(String fiscalImpact) {
		this.fiscalImpact = fiscalImpact;
	}

	public Float getFiscalTotal() {
		if (fiscalTotal == null) {
			return 0f;
		}
		return fiscalTotal;
	}

	public void setFiscalTotal(Float fiscalTotal) {
		this.fiscalTotal = fiscalTotal;
	}

	public String getOwnerDesc() {
		return ownerDesc;
	}

	public void setOwnerDesc(String ownerDesc) {
		this.ownerDesc = ownerDesc;
	}

	public String getNewlyOwnedBy() {
		return newlyOwnedBy;
	}

	public void setNewlyOwnedBy(String newlyOwnedBy) {
		this.newlyOwnedBy = newlyOwnedBy;
	}

	public String getNewlyOwnedAction() {
		return newlyOwnedAction;
	}

	public void setNewlyOwnedAction(String newlyOwnedAction) {
		this.newlyOwnedAction = newlyOwnedAction;
	}

	public String getSponsorChamber() {
		return sponsorChamber;
	}

	public void setSponsorChamber(String sponsorChamber) {
		this.sponsorChamber = sponsorChamber;
	}

	public String getLeadershipPosition() {
		return leadershipPosition;
	}

	public void setLeadershipPosition(String leadershipPosition) {
		this.leadershipPosition = leadershipPosition;
	}

	public String getFlLeadershipPosition() {
		return flLeadershipPosition;
	}

	public void setFlLeadershipPosition(String flLeadershipPosition) {
		this.flLeadershipPosition = flLeadershipPosition;
	}

	public String getHRead1() {
		return HRead1;
	}

	public void setHRead1(String hRead1) {
		HRead1 = hRead1;
	}

	public String getHRead2() {
		return HRead2;
	}

	public void setHRead2(String hRead2) {
		HRead2 = hRead2;
	}

	public String getHRead3() {
		return HRead3;
	}

	public void setHRead3(String hRead3) {
		HRead3 = hRead3;
	}

	public String getHPass3() {
		return HPass3;
	}

	public void setHPass3(String hPass3) {
		HPass3 = hPass3;
	}

	public Integer getH3Sub() {
		return H3Sub;
	}

	public void setH3Sub(Integer h3Sub) {
		H3Sub = h3Sub;
	}

	public Integer getH3Amd() {
		return H3Amd;
	}

	public void setH3Amd(Integer h3Amd) {
		H3Amd = h3Amd;
	}

	public String getHPassFinal() {
		return HPassFinal;
	}

	public void setHPassFinal(String hPassFinal) {
		HPassFinal = hPassFinal;
	}

	public String getHPassAction() {
		return HPassAction;
	}

	public void setHPassAction(String hPassAction) {
		HPassAction = hPassAction;
	}

	public String getHComCode() {
		return HComCode;
	}

	public void setHComCode(String hComCode) {
		HComCode = hComCode;
	}

	public String getHComAction() {
		return HComAction;
	}

	public void setHComAction(String hComAction) {
		HComAction = hComAction;
	}

	public String getHComActionDate() {
		return HComActionDate;
	}

	public void setHComActionDate(String hComActionDate) {
		HComActionDate = hComActionDate;
	}

	public Integer getHCASub() {
		return HCASub;
	}

	public void setHCASub(Integer hCASub) {
		HCASub = hCASub;
	}

	public Integer getHCAAmd() {
		return HCAAmd;
	}

	public void setHCAAmd(Integer hCAAmd) {
		HCAAmd = hCAAmd;
	}

	public String getHCRDate() {
		return HCRDate;
	}

	public void setHCRDate(String hCRDate) {
		HCRDate = hCRDate;
	}

	public Integer getHCRSub() {
		return HCRSub;
	}

	public void setHCRSub(Integer hCRSub) {
		HCRSub = hCRSub;
	}

	public Integer getHCRAmd() {
		return HCRAmd;
	}

	public void setHCRAmd(Integer hCRAmd) {
		HCRAmd = hCRAmd;
	}

	public String getSRead1() {
		return SRead1;
	}

	public void setSRead1(String sRead1) {
		SRead1 = sRead1;
	}

	public String getSRead2() {
		return SRead2;
	}

	public void setSRead2(String sRead2) {
		SRead2 = sRead2;
	}

	public String getSRead3() {
		return SRead3;
	}

	public void setSRead3(String sRead3) {
		SRead3 = sRead3;
	}

	public String getSPass2() {
		return SPass2;
	}

	public void setSPass2(String sPass2) {
		SPass2 = sPass2;
	}

	public Integer getS2Sub() {
		return S2Sub;
	}

	public void setS2Sub(Integer s2Sub) {
		S2Sub = s2Sub;
	}

	public Integer getS2Amd() {
		return S2Amd;
	}

	public void setS2Amd(Integer s2Amd) {
		S2Amd = s2Amd;
	}

	public String getSPass3() {
		return SPass3;
	}

	public void setSPass3(String sPass3) {
		SPass3 = sPass3;
	}

	public Integer getS3Sub() {
		return S3Sub;
	}

	public void setS3Sub(Integer s3Sub) {
		S3Sub = s3Sub;
	}

	public Integer getS3Amd() {
		return S3Amd;
	}

	public void setS3Amd(Integer s3Amd) {
		S3Amd = s3Amd;
	}

	public String getSPassFinal() {
		return SPassFinal;
	}

	public void setSPassFinal(String sPassFinal) {
		SPassFinal = sPassFinal;
	}

	public String getSPassAction() {
		return SPassAction;
	}

	public void setSPassAction(String sPassAction) {
		SPassAction = sPassAction;
	}

	public String getSComCode() {
		return SComCode;
	}

	public void setSComCode(String sComCode) {
		SComCode = sComCode;
	}

	public String getSComAction() {
		return SComAction;
	}

	public void setSComAction(String sComAction) {
		SComAction = sComAction;
	}

	public String getSComActionDate() {
		return SComActionDate;
	}

	public void setSComActionDate(String sComActionDate) {
		SComActionDate = sComActionDate;
	}

	public Integer getSCASub() {
		return SCASub;
	}

	public void setSCASub(Integer sCASub) {
		SCASub = sCASub;
	}

	public Integer getSCAAmd() {
		return SCAAmd;
	}

	public void setSCAAmd(Integer sCAAmd) {
		SCAAmd = sCAAmd;
	}

	public String getSCRDate() {
		return SCRDate;
	}

	public void setSCRDate(String sCRDate) {
		SCRDate = sCRDate;
	}

	public Integer getSCRSub() {
		return SCRSub;
	}

	public void setSCRSub(Integer sCRSub) {
		SCRSub = sCRSub;
	}

	public Integer getSCRAmd() {
		return SCRAmd;
	}

	public void setSCRAmd(Integer sCRAmd) {
		SCRAmd = sCRAmd;
	}

	public String getConcur() {
		return Concur;
	}

	public void setConcur(String concur) {
		Concur = concur;
	}

	public String getPassDate() {
		return PassDate;
	}

	public void setPassDate(String passDate) {
		PassDate = passDate;
	}

	public String getGovAction() {
		return GovAction;
	}

	public void setGovAction(String govAction) {
		GovAction = govAction;
	}

	public String getGovDate() {
		return GovDate;
	}

	public void setGovDate(String govDate) {
		GovDate = govDate;
	}

	public String getOverRide() {
		return OverRide;
	}

	public void setOverRide(String overRide) {
		OverRide = overRide;
	}

	public String getFailedOnFloorDate() {
		return failedOnFloorDate;
	}

	public void setFailedOnFloorDate(String failedOnFloorDate) {
		this.failedOnFloorDate = failedOnFloorDate;
	}

	public String getFailedOnFloorAction() {
		return failedOnFloorAction;
	}

	public void setFailedOnFloorAction(String failedOnFloorAction) {
		this.failedOnFloorAction = failedOnFloorAction;
	}
	
	public String getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(String subjectList) {
		this.subjectList = subjectList;
	}

	public String getSectionsAffected() {
		return sectionsAffected;
	}

	public void setSectionsAffected(String sectionsAffected) {
		this.sectionsAffected = sectionsAffected;
	}

	public String getClauses() {
		return clauses;
	}

	public void setClauses(String clauses) {
		this.clauses = clauses;
	}

	public String getProvisions() {
		if (provisions != null) {
			return provisions.replaceAll("<[^>]*>", " ");
		}
		return provisions;
	}

	public void setProvisions(String provisions) {
		this.provisions = provisions;
	}
	
	public String getMonies() {
		if (monies != null) {
			return monies.replaceAll("<[^>]*>", " ");
		}
		return monies;
	}

	public void setMonies(String monies) {
		this.monies = monies;
	}

}