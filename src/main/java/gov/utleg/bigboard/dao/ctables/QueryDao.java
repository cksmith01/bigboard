package gov.utleg.bigboard.dao.ctables;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import gov.utleg.bigboard.Constants;
import gov.utleg.bigboard.model.BillItem;
import gov.utleg.bigboard.model.Committee;
import gov.utleg.bigboard.model.LastAction;
import gov.utleg.bigboard.model.ListItem;
import gov.utleg.bigboard.model.ReadingCalItem;
import gov.utleg.bigboard.model.SessionDate;

@Mapper
public interface QueryDao {

	/*
	 * Currently this query returns duplicate rows AND I'm not sure which join is causing this
	 */
	public static final String BILL_LIST_2024 = """
			WITH
			SectionsList AS (
			    SELECT DocNumber, STRING_AGG(EditedSection, ', ') AS SectionsAffected
			    FROM Itables${year}.dbo.SectionsAffected
			    where type<>'E'
			    GROUP BY DocNumber
			),
			SubjectList AS (
			    SELECT s.FileNumber, STRING_AGG(sm.[Description], ', ') AS Subjects
			    FROM Sessions.dbo.SubjectIndex s
			        LEFT OUTER JOIN Ctables.dbo.SubjectMaster sm ON s.Subject=sm.SubjectCode
			    WHERE s.SessionID LIKE '${year}%'
			    GROUP BY s.FileNumber
			), 
			MostRecentAction AS (
			    SELECT ah.FileNumber, ah.ActionCode, ah.ActionDate, ah.[Description], ah.DocNumber, ah.[Owner]
			    FROM
			        (SELECT FileNumber, MAX(ActionDate) AS created_at
			        FROM Sessions.dbo.ActionHistory
			        GROUP BY FileNumber) AS latest_actions
			    INNER JOIN
			    	Sessions.dbo.ActionHistory ah ON (ah.FileNumber = latest_actions.FileNumber AND ah.ActionDate = latest_actions.created_at)
			    WHERE ah.SessionID LIKE '${year}%'
			),
            FailedOnFloor AS (
                select FileNumber, DocNumber, ActionCode, ActionDate
                from Sessions.dbo.ActionHistory
                where SessionID = '${year}GS'
                    and ActionCode IN ('SFAIL', 'HFAIL')
            )
			SELECT 
			    b.sessionID, b.fileNumber, b.billNumber, bs.realBillNumber, dmv.shortTitle
			    , FORMAT(b.BillEffectiveDate, 'yyyy-MM-dd') AS effectiveDate
			    , m.Title AS longTitle, ISNULL(m.MoniesAppropriated, 'None') AS monies
			    , lh.FullName AS primarySponsor, lh2.FullName AS floorSponsor
			    , s.FullName AS attorney, s2.FullName AS lrgcAnalyst, s3.FullName AS lfaAnalyst, b.isPublic
			    , ISNULL(ah.[Description], '') AS actionCodeDesc, ISNULL(ah.ActionCode, '') AS actionCode, CONVERT(varchar, ah.ActionDate, 20) AS lastActionDate, ISNULL(om.Description, '') AS actionDesc
			    , ISNULL(sl.Subjects, '') AS subjectList, b.impact
			    , ISNULL(sec.SectionsAffected, '') AS sectionsAffected
			    , REPLACE(CONCAT('https://le.utah.gov/~',b.SessionID,'/bills/static/', b.BillNumber, '.html'), 'GS/', '/' ) AS link
			    , CONCAT('https://glen.le.utah.gov/bills/',b.SessionID,'/', b.BillNumber, '/') AS json
			    , lh.LegislatorID AS sponsorID, lh2.LegislatorID AS floorSponsorID
			    , b.[Owner]
			    , ROUND(fi.Onetime, 2) AS onetime, ROUND(fi.Ongoing, 2) AS ongoing
			    , ROUND((fi.Onetime + fi.Ongoing), 2) AS fiscalTotal
			    , om.[Description] AS ownerDesc
			    , m.HighlightedProvisions AS provisions
                , ISNULL(fi.fiscalBill, '') AS fiscalBill, ISNULL(fi.FiscalImpact, '') AS fiscalImpact
                , lh.ProfessionalExtension AS sponsorChamber
                , ISNULL(lh.LeadershipPosition, '') AS leadershipPosition, ISNULL(lh2.LeadershipPosition, '') AS flLeadershipPosition
                , CONVERT(varchar, bs.HRead1, 20) AS HRead1
                , CONVERT(varchar, bs.HRead2, 20) AS HRead2
                , CONVERT(varchar, bs.HRead3, 20) AS HRead3
                , CONVERT(varchar, bs.HPass3, 20) AS HPass3
                , bs.H3Sub, bs.H3Amd
                , CONVERT(varchar, bs.HPassFinal, 20) AS HPassFinal
                , bs.HPassAction, bs.HComCode, bs.HComAction
                , CONVERT(varchar, bs.HComActionDate, 20) AS HComActionDate
                , bs.HCASub, bs.HCAAmd
                , CONVERT(varchar, bs.HCRDate, 20) AS HCRDate
                , bs.HCRSub, bs.HCRAmd
                , CONVERT(varchar, bs.SRead1, 20) AS SRead1
                , CONVERT(varchar, bs.SRead2, 20) AS SRead2
                , CONVERT(varchar, bs.SRead3, 20) AS SRead3
                , CONVERT(varchar, bs.SPass2, 20) AS SPass2
                , bs.S2Sub, bs.S2Amd
                , CONVERT(varchar, bs.SPass3, 20) AS SPass3
                , bs.S3Sub, bs.S3Amd
                , CONVERT(varchar, bs.SPassFinal, 20) AS SPassFinal
                , bs.SPassAction, bs.SComCode, bs.SComAction
                , CONVERT(varchar, bs.SComActionDate, 20) AS SComActionDate
                , bs.SCASub, bs.SCAAmd
                , CONVERT(varchar, bs.SCRDate, 20) AS SCRDate
                , bs.SCRSub, bs.SCRAmd
                , CONVERT(varchar, bs.Concur, 20) AS Concur
                , CONVERT(varchar, bs.PassDate, 20) AS PassDate
                , bs.GovAction
                , CONVERT(varchar, bs.GovDate, 20) AS GovDate
                , CONVERT(varchar, bs.OverRide, 20) AS OverRide
                , fof.ActionDate AS failedOnFloorDate, fof.ActionCode AS failedOnFloorAction
			FROM Sessions.dbo.DocMasterGlobal b
			    LEFT OUTER JOIN Ctables.dbo.LegislatorHistorical lh ON (b.PrimeSponsor = lh.LegislatorID AND lh.[Session] = '${year}')
			    LEFT OUTER JOIN Ctables.dbo.LegislatorHistorical lh2 ON (b.FloorSponsor = lh2.LegislatorID AND lh2.[Session] = '${year}')
			    LEFT OUTER JOIN Ctables.dbo.Staff s ON (b.Attorney = s.UserID)
			    LEFT OUTER JOIN Ctables.dbo.Staff s2 ON (b.TrackingAnalyst = s2.UserID)
			    LEFT OUTER JOIN Ctables.dbo.Staff s3 ON (b.FiscalAnalyst = s3.UserID)
			    LEFT OUTER JOIN Sessions.dbo.DocMasterVersion dmv ON (b.FileNumber = dmv.FileNumber AND dmv.SessionID=b.SessionID)
			    LEFT OUTER JOIN Sessions.dbo.DocMasterMemo m ON (b.FileNumber = m.FileNumber AND m.SessionID=b.SessionID AND m.SubVersion=0)
			    LEFT OUTER JOIN LFAFiscalNotes.dbo.vFiscalImpactAll fi ON (b.FileNumber=fi.FileNumber AND b.BillNumber=fi.BillNumber AND fi.SessionID=b.SessionID)
			    LEFT OUTER JOIN MostRecentAction ah ON (ah.FileNumber = b.FileNumber)
			    LEFT OUTER JOIN SubjectList sl ON (sl.FileNumber=b.FileNumber)
			    LEFT OUTER JOIN SectionsList sec ON (sec.DocNumber=b.BillNumber)
			    LEFT OUTER JOIN Sessions.dbo.BillStatus bs ON (bs.SessionID=b.SessionID AND bs.FileNumber=b.FileNumber)
			    LEFT OUTER JOIN Ctables.dbo.OwnerMaster om ON (b.Owner=om.OwnerID)
                LEFT OUTER JOIN FailedOnFloor fof ON (fof.FileNumber=b.FileNumber)
			WHERE
			    b.SessionID IN (SELECT distinct(SessionID) FROM Ctables.dbo.Wheresitatv WHERE SessionID LIKE '${year}%')
			    AND b.BillNumber IS NOT NULL
			    AND b.isPublic = 1
			    AND bs.realBillNumber IS NOT NULL
			ORDER BY b.BillNumber DESC
			""";

	@Select(BILL_LIST_2024)
	List<BillItem> getBillsFor2024(@Param("year") Integer year);
	
	public static final String BILL_LIST_2025 = """
			
WITH
			distSubjects AS (
				SELECT distinct([Subject]), FileNumber
				FROM Sessions.dbo.SubjectIndex
				WHERE FileNumber LIKE '${year}FL%'
				GROUP BY FileNumber, [Subject]
			),
			SectionsList AS (
				SELECT FileNumber, STRING_AGG(SecNo, ', ') AS SectionsAffected
				FROM BillXML.dbo.SectionsAffected
				WHERE sessionID='${year}GS' AND MinorType = 'section' 
					AND SecNo <> '' AND SecNo <> 'effdate'
					AND SubVersion = 0 and MinVersion = 0
				GROUP BY FileNumber
			),
			SubjectList AS (
				SELECT s.FileNumber, STRING_AGG(sm.[Description], ', ') AS Subjects
				FROM distSubjects s
					LEFT OUTER JOIN Ctables.dbo.SubjectMaster sm ON s.Subject=sm.SubjectCode
				GROUP BY s.FileNumber
			), 
			EffectiveDate AS (
				SELECT FileNumber, MIN(EffectiveDate) as eff_date
				FROM BillXML.dbo.SectionsAffected
				WHERE sessionID='${year}GS' and secno = 'effdate'
					and EffectiveDate > '${year}-01-01'
				GROUP BY FileNumber
			),
			MostRecentAction AS (
				SELECT ah.FileNumber, ah.ActionCode, ah.ActionDate, ah.[Description], ah.DocNumber, ah.[Owner]
				FROM
					(SELECT FileNumber, MAX(Sequence) AS seq
					FROM Sessions.dbo.ActionHistory
					WHERE FileNumber like '${year}%'
					GROUP BY FileNumber) AS latest_actions
				INNER JOIN
					Sessions.dbo.ActionHistory ah ON (ah.FileNumber = latest_actions.FileNumber AND ah.sequence = latest_actions.seq)
				WHERE ah.SessionID LIKE '${year}%'
			),
			FailedOnFloor AS (
				SELECT FileNumber, DocNumber, ActionCode, ActionDate
				FROM Sessions.dbo.ActionHistory
				WHERE SessionID = '${year}GS'
					AND ActionCode IN ('SFAIL', 'HFAIL')
			)
			SELECT 
				b.sessionID, b.fileNumber, b.billNumber, bs.realBillNumber, dmv.shortTitle
				, FORMAT(ed.eff_date, 'yyyy-MM-dd') AS effectiveDate
				, m.Title AS longTitle, ISNULL(m.MoniesAppropriated, 'None') AS monies
				, lh.FullName AS primarySponsor, lh2.FullName AS floorSponsor
				, s.FullName AS attorney, s2.FullName AS lrgcAnalyst, s3.FullName AS lfaAnalyst, b.isPublic
				, ISNULL(ah.[Description], '') AS actionCodeDesc, ISNULL(ah.ActionCode, '') AS actionCode, CONVERT(varchar, ah.ActionDate, 20) AS lastActionDate
				, ISNULL(om.Description, '') AS actionDesc
				, ISNULL(sl.Subjects, '') AS subjectList, b.impact
				, ISNULL(sec.SectionsAffected, '') AS sectionsAffected
				, REPLACE(CONCAT('https://le.utah.gov/~',b.SessionID,'/bills/static/', b.BillNumber, '.html'), 'GS/', '/' ) AS link
				, CONCAT('https://glen.le.utah.gov/bills/',b.SessionID,'/', b.BillNumber, '/') AS json
				, lh.LegislatorID AS sponsorID, lh2.LegislatorID AS floorSponsorID
				, b.[Owner]
				, ROUND(fi.Onetime, 2) AS onetime, ROUND(fi.Ongoing, 2) AS ongoing
				, ROUND((fi.Onetime + fi.Ongoing), 2) AS fiscalTotal
				, om.[Description] AS ownerDesc
				, m.HighlightedProvisions AS provisions
				, ISNULL(fi.fiscalBill, '') AS fiscalBill, ISNULL(fi.FiscalImpact, '') AS fiscalImpact
				, lh.ProfessionalExtension AS sponsorChamber
				, ISNULL(lh.LeadershipPosition, '') AS leadershipPosition, ISNULL(lh2.LeadershipPosition, '') AS flLeadershipPosition
				, CONVERT(varchar, bs.HRead1, 20) AS HRead1
				, CONVERT(varchar, bs.HRead2, 20) AS HRead2
				, CONVERT(varchar, bs.HRead3, 20) AS HRead3
				, CONVERT(varchar, bs.HPass3, 20) AS HPass3
				, bs.H3Sub, bs.H3Amd
				, CONVERT(varchar, bs.HPassFinal, 20) AS HPassFinal
				, bs.HPassAction, bs.HComCode, bs.HComAction
				, CONVERT(varchar, bs.HComActionDate, 20) AS HComActionDate
				, bs.HCASub, bs.HCAAmd
				, CONVERT(varchar, bs.HCRDate, 20) AS HCRDate
				, bs.HCRSub, bs.HCRAmd
				, CONVERT(varchar, bs.SRead1, 20) AS SRead1
				, CONVERT(varchar, bs.SRead2, 20) AS SRead2
				, CONVERT(varchar, bs.SRead3, 20) AS SRead3
				, CONVERT(varchar, bs.SPass2, 20) AS SPass2
				, bs.S2Sub, bs.S2Amd
				, CONVERT(varchar, bs.SPass3, 20) AS SPass3
				, bs.S3Sub, bs.S3Amd
				, CONVERT(varchar, bs.SPassFinal, 20) AS SPassFinal
				, bs.SPassAction, bs.SComCode, bs.SComAction
				, CONVERT(varchar, bs.SComActionDate, 20) AS SComActionDate
				, bs.SCASub, bs.SCAAmd
				, CONVERT(varchar, bs.SCRDate, 20) AS SCRDate
				, bs.SCRSub, bs.SCRAmd
				, CONVERT(varchar, bs.Concur, 20) AS Concur
				, CONVERT(varchar, bs.PassDate, 20) AS PassDate
				, bs.GovAction
				, CONVERT(varchar, bs.GovDate, 20) AS GovDate
				, CONVERT(varchar, bs.OverRide, 20) AS OverRide
				, fof.ActionDate AS failedOnFloorDate, fof.ActionCode AS failedOnFloorAction
			FROM Sessions.dbo.DocMasterGlobal b
				LEFT OUTER JOIN Sessions.dbo.DocMasterVersion dmv ON (b.FileNumber = dmv.FileNumber and b.CurrentSubVersion=dmv.SubVersion)
				LEFT OUTER JOIN Sessions.dbo.DocMasterMemo m ON (b.FileNumber = m.FileNumber AND b.CurrentSubVersion=m.SubVersion)
				LEFT OUTER JOIN Sessions.dbo.BillStatus bs ON (bs.FileNumber=b.FileNumber)
				LEFT OUTER JOIN Ctables.dbo.LegislatorHistorical lh ON (b.PrimeSponsor = lh.LegislatorID AND lh.[Session] = '${year}')
				LEFT OUTER JOIN Ctables.dbo.LegislatorHistorical lh2 ON (b.FloorSponsor = lh2.LegislatorID AND lh2.[Session] = '${year}')
				LEFT OUTER JOIN Ctables.dbo.Staff s ON (b.Attorney = s.UserID)
				LEFT OUTER JOIN Ctables.dbo.Staff s2 ON (b.TrackingAnalyst = s2.UserID)
				LEFT OUTER JOIN Ctables.dbo.Staff s3 ON (b.FiscalAnalyst = s3.UserID)
				LEFT OUTER JOIN LFAFiscalNotes.dbo.vFiscalImpactAll fi ON (b.FileNumber=fi.FileNumber AND bs.RealBillNumber=fi.BillNumber)
				LEFT OUTER JOIN EffectiveDate ed ON (b.FileNumber = ed.FileNumber)
				LEFT OUTER JOIN MostRecentAction ah ON (ah.FileNumber = b.FileNumber)
				LEFT OUTER JOIN SubjectList sl ON (sl.FileNumber=b.FileNumber)
				LEFT OUTER JOIN SectionsList sec ON (sec.FileNumber=b.FileNumber)
				LEFT OUTER JOIN Ctables.dbo.OwnerMaster om ON (b.Owner=om.OwnerID)
				LEFT OUTER JOIN FailedOnFloor fof ON (fof.FileNumber=b.FileNumber)
			WHERE
				b.SessionID IN (SELECT distinct(SessionID) FROM Ctables.dbo.Wheresitatv WHERE SessionID LIKE '${year}%')
				AND b.BillNumber IS NOT NULL
				AND b.isPublic = 1
				AND bs.realBillNumber IS NOT NULL
			ORDER BY b.BillNumber DESC
			""";
	
	@Select(BILL_LIST_2025)
	List<BillItem> getBillsFor2025(@Param("year") Integer year);
	
	
	public static final String GROUPS_QUERY = """
			SELECT groups
			FROM ApplTables.dbo.BasicUserInfo
			WHERE
			    [Login] IN (SELECT UserID
			        FROM Ctables.dbo.Staff
			        WHERE ActiveInactive='A')
			""";
	@Select(GROUPS_QUERY)
	List<String> getGroups();
	
	public static final String USERS_FOR_GROUP = """
			SELECT CONCAT(s.FullName, ', ', s.Position, ', ', s.Office) 
			FROM ApplTables.dbo.BasicUserInfo bi
			    INNER JOIN Ctables.dbo.Staff s ON (bi.Login = s.UserID) 
			WHERE bi.groups LIKE '%${groupName}%' AND s.ActiveInactive='A'
			""";
	@Select(USERS_FOR_GROUP)
	List<String> getUsersForGroup(@Param("groupName") String groupName);
	
	
	public static final String COMMITTEE_LIST = """
		SELECT distinct(OwnerID), TRIM(REPLACE([Description], ' Committee', '')) AS Description
		FROM Ctables.dbo.OwnerMaster
		WHERE OwnerID IN (
		    SELECT distinct(ComCode)
		    FROM Sessions.dbo.CommitteePossession
		    WHERE SessionID LIKE '%${year}%'
		)
		OR OwnerID IN ('HSTRUL','SSTRUL')
		ORDER BY 1
			""";
	@Cacheable(Constants.CACHE_COMMITTEE_LIST)
	@Select(COMMITTEE_LIST)
	List<Committee> getCommitteeList(@Param("year") Integer year);
	
	
//	public static final String COMM_BILLS = """
//			with commBills AS (
//			    SELECT distinct(ifi.BillFile), ifi.MtgID
//			    FROM Ctables.dbo.IntFileIndex ifi
//			    where ifi.BillFile LIKE '${year}%'
//			)
//			SELECT CONCAT(cb.MtgID, '|', STRING_AGG(cb.BillFile, ',')) AS FileList
//			from commBills cb
//			group by cb.MtgID
//			""";
//	@Select(COMM_BILLS)
//	List<String> getCommBills(@Param("year") Integer year);
	
	
	public static final String LAST_ACTION = """
				SELECT TOP 1 ah.ActionCode, CONVERT(varchar, ah.ActionDate, 20) AS ActionDate, ah.[Description], om.[Description] AS location
				FROM Sessions.dbo.ActionHistory ah
				    LEFT JOIN Ctables.dbo.OwnerMaster om ON (om.OwnerID = ah.[Owner])
				WHERE SessionID='${sessionId}' AND DocNumber='${docNumber}'
				ORDER BY ActionDate DESC
			""";
	@Select(LAST_ACTION)
	LastAction getLastAction(@Param("sessionId") String sessionId, @Param("docNumber") String docNumber);
	
	public static final String CALENDAR_LIST = """
			SELECT OwnerID AS id, [Description] AS label
			FROM Ctables.dbo.OwnerMaster
			WHERE Description LIKE '%calendar%'
			    AND Description NOT LIKE '%calendar table%'
			    AND Description NOT LIKE '%special%'
			ORDER BY 2
			""";
	@Select(CALENDAR_LIST)
	List<ListItem> getCalendarList();
	
	public static final String CALENDAR_BILL_ORDER = """
			SELECT CalID, BillNo, Seq FROM Itables${year}.dbo.CalendarData order by CalID, Seq ASC
			""";
	@Select(CALENDAR_BILL_ORDER)
	List<ReadingCalItem> getCalendarBillOrder(@Param("year") Integer year);
	
	public static final String SESSION_START_AND_END_DATES = """
			SELECT sd.ItemDate, sd.SessionDay
			FROM (
			    SELECT SessionDay, SessionYear, max(SortOrder) AS sortOrder
			    FROM Ctables.dbo.SessionDates
			    WHERE SessionYear=${year}
			    GROUP BY SessionDay, SessionYear
			    ) AS max_sess_date
			INNER JOIN Ctables.dbo.SessionDates sd ON 
				(sd.SessionYear = max_sess_date.SessionYear AND sd.SessionDay = max_sess_date.SessionDay AND sd.SortOrder = max_sess_date.sortOrder)
			WHERE sd.SessionYear=${year} AND sd.SessionDay in (1,45)
			""";
	@Cacheable(Constants.CACHE_SESSION_DATES)
	@Select(SESSION_START_AND_END_DATES)
	List<SessionDate> getSessionStartAndEndDates(@Param("year") Integer year);
	
}
