
package acme.components;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import acme.entities.banners.Banner;
import acme.framework.repositories.AbstractRepository;

public interface BannerRepository extends AbstractRepository {

	@Query("select count(b) from Banner b")
	int countBanners();

	@Query("select b from Banner b, Patron p where b.patron=p " + "and substring(p.expirationDate,4,4) > substring(CURRENT_DATE,1,4) "
		+ "or substring(p.expirationDate,4,4) = substring(CURRENT_DATE,1,4) and substring(p.expirationDate,1,2) >= substring(CURRENT_DATE,6,2)")
	List<Banner> findManyBannersWithValidCreditCard(PageRequest pageRequest);

	default Banner findRandomBanner() {
		Banner result;
		int bannerCount, bannerIndex;
		ThreadLocalRandom random;
		PageRequest page;
		List<Banner> list;

		bannerCount = this.countBanners();
		random = ThreadLocalRandom.current();
		bannerIndex = random.nextInt(0, bannerCount);

		page = PageRequest.of(bannerIndex, 1);
		list = this.findManyBannersWithValidCreditCard(page);
		result = list.isEmpty() ? null : list.get(0);

		return result;
	}
}
