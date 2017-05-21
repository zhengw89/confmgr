package zhengw.confmgr.bean;

import org.springframework.data.domain.Page;

public class PaginationViewModel<T> {

	private static final int DEFAULT_BUFFER = 5;

	private final int startPageIndex;

	private final int currentPageIndex;

	private final int endPageIndex;

	private final Page<T> page;

	public Page<T> getPage() {
		return this.page;
	}

	public int getStartPageIndex() {
		return this.startPageIndex;
	}

	public int getCurrentPageIndex() {
		return this.currentPageIndex;
	}

	public int getEndPageIndex() {
		return this.endPageIndex;
	}

	public PaginationViewModel(Page<T> page) {
		this.page = page;
		this.startPageIndex = Math.max(1, page.getNumber() + 1 - DEFAULT_BUFFER);
		this.currentPageIndex = page.getNumber() + 1;
		this.endPageIndex = Math.min(page.getTotalPages(), page.getNumber() + 1 + DEFAULT_BUFFER);
	}

	public boolean isFirst() {
		return this.page.isFirst();
	}

	public boolean isLast() {
		return this.page.isLast();
	}

	public int getTotalPages() {
		return this.page.getTotalPages();
	}

	public long getTotalElements() {
		return this.page.getTotalElements();
	}
}
