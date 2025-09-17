package gov.utleg.bigboard.controller;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.utleg.bigboard.dao.ctables.ActionCodeDao;
import gov.utleg.bigboard.dao.ctables.QueryDao;
import gov.utleg.bigboard.model.ActionCode;
import gov.utleg.bigboard.model.ActionOwnerCode;
import gov.utleg.bigboard.model.BillItem;
import gov.utleg.bigboard.model.Committee;
import gov.utleg.bigboard.model.ListItem;
import gov.utleg.bigboard.model.ReadingCalItem;
import gov.utleg.bigboard.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillsController extends BaseController {

	@Autowired
	BillService billService;
	
	@Autowired
	QueryDao queryDao;

	@Autowired
	ActionCodeDao actionCodeDao;

	@GetMapping("/list/{year}")
	public Collection<BillItem> billList(@PathVariable Integer year) {
		/* todo: 
		 * 	this returns no data for 2021, and a few years back.  There is a gap in Sessions.DocMasterGlobal (or maybe DocMasterMemo)
		 * 	that I think needs to be fixed at some point. Consider, writing a different query for previous years 
		 */
		return billService.getBillList(year);
	}

	@GetMapping("/actionCodes/{year}")
	public Collection<ActionCode> actionCodes(@PathVariable Integer year) {
		return actionCodeDao.getActiveActionCodes(year);
	}
	@GetMapping("/actionAndOwnerCodes/")
	public Collection<ActionOwnerCode> actionAndOwnerCodes() {
		return actionCodeDao.getActionAndOwnerCodes();
	}

	@GetMapping("/committee/list/{year}")
	public List<Committee> commmitteeList(@PathVariable Integer year) {
		return queryDao.getCommitteeList(year);
	}
	
	@GetMapping("/calendar/list/")
	public List<ListItem> calendarList() {
		return billService.getCalendarList();
	}
	
	@GetMapping("/calendar/bill/order/")
	public List<ReadingCalItem> calendarBillOrder() {
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		return billService.getCalendarBillOrder(year);
	}
	
}
