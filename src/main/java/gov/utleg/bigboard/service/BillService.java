package gov.utleg.bigboard.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import gov.utleg.bigboard.Constants;
import gov.utleg.bigboard.dao.ctables.QueryDao;
import gov.utleg.bigboard.model.BillItem;
import gov.utleg.bigboard.model.LastAction;
import gov.utleg.bigboard.model.ListItem;
import gov.utleg.bigboard.model.ReadingCalItem;
import gov.utleg.bigboard.util.Strings;

@Service
public class BillService {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private boolean debug;

	@Autowired
	QueryDao queryDao;
	
	@Cacheable(Constants.CACHE_BILL_LIST)
	public Collection<BillItem> getBillList(Integer year) {

		Collection<BillItem> list = null;
		
		if (year < 2025) {
			list = queryDao.getBillsFor2024(year);	
		} else {
			list = queryDao.getBillsFor2025(year);
		}
		
//		return list;
		
		TreeMap<String, BillItem> filteredList = new TreeMap<String, BillItem>();

		int dupes = 0;
		int missing = 0;

		for (Iterator<BillItem> iterator = list.iterator(); iterator.hasNext();) {
			BillItem bill = (BillItem) iterator.next();

			if (!filteredList.containsKey(bill.getBillNumber())) {
				boolean add = true;
				if (add && Strings.isBlank(bill.getActionCode())) {
					add = false;
					missing++;
					LastAction last = queryDao.getLastAction(bill.getSessionID(), bill.getBillNumber());
					if (last != null) {
						add = true;
						bill.setActionCode(last.getActionCode());
						bill.setActionCodeDesc(last.getDescription());
						bill.setActionDesc(last.getLocation());
						bill.setLastActionDate(last.getActionDate());
					} else {
//						logger.info("Ignored: " + bill.getBillNumber()+" it doesn't have a last action");
					}
				}

				if (add)
					filteredList.put(bill.getBillNumber(), bill);

			} else {
				dupes++;
			}

		}

		if (debug) {
			logger.info("Total: " + list.size() + " Duplicates: " + dupes + " Missing actions: " + missing);
		}

		return filteredList.values();
	}
	
	@Cacheable(Constants.CACHE_CALENDAR_LIST)
	public List<ListItem> getCalendarList() {
		return queryDao.getCalendarList();
	}
	
	public List<ReadingCalItem> getCalendarBillOrder(Integer year) {
		return queryDao.getCalendarBillOrder(year);
	}
	
}
