package gov.utleg.bigboard;

import java.util.Date;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Query result caching configuration so that end users can't hit the database
 * too hard
 * 
 * @author csmith
 */

@Configuration
@EnableCaching
public class CachingConfig {

	private static final int MINUTES_TO_CACHE = (60 * 1000) * 10;

//	Logger log = Logger.getLogger(this.getClass().getCanonicalName());

	/*
	 * @Todo Add smart caching based on time of year. During session every 10
	 * minutes is good, but after session the data doesn't change much, and then
	 * after the governor signs caches should never clear b/c the data doesn't
	 * change at all
	 */

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager(Constants.CACHE_BILL_LIST, Constants.CACHE_ACTION_CODES,
				Constants.CACHE_COMMITTEE_LIST, Constants.CACHE_CALENDAR_LIST, Constants.CACHE_SESSION_DATES, 
				Constants.CACHE_COMBINED_ACTION_AND_OWNER_CODES_LIST);
	}

	@Scheduled(fixedRate = MINUTES_TO_CACHE)
	@CacheEvict(value = Constants.CACHE_BILL_LIST, allEntries = true)
	public void clearBillListCache() {
		UsageInfo.getInstance().addCacheCleardDate(new Date());
	}

	@Scheduled(fixedRate = MINUTES_TO_CACHE)
	@CacheEvict(value = Constants.CACHE_ACTION_CODES, allEntries = true)
	public void clearActionCodeCache() {
	}

	/**
	 * These don't need to refresh ...
	 */

	public void clearCommitteeListCache() {}

	public void clearCalendarListCache() {}

	public void clearSessionDatesCache() {}
	
	public void clearActionAndOnwerCodesCache() {}
}
