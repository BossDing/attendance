package com.kay.common.pape;

import java.util.Collections;
import java.util.List;

/**
 * Created by kay on 2018/3/6.
 */
public class PageQueryBean {

    public static final int DEFAULT_PAGE_SIZE = 10;  //默认行数

    private Integer currentPage;

    private Integer pageSize;

    private int totalRows;

    private Integer startRow;

    private Integer totalPage;

    private List<?> items;

    public final Integer getStartRow() {
        if(startRow == null){
            startRow = (currentPage == null ? 0 : (currentPage - 1) * getPageSize());
        }
        return startRow;
    }

    public final void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public final Integer getCurrentPage() {
        return currentPage;
    }

    public final void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public final Integer getPageSize() {
        return pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public final void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public final int getTotalRows() {
        return totalRows;
    }

    public final void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
        int totalPage = totalRows % getPageSize() == 0 ? totalRows / getPageSize() : (totalRows / getPageSize() + 1);
        setTotalPage(totalPage);
    }

    public final Integer getTotalPage() {
        return totalPage == null || totalPage == 0 ? 1 : totalPage;
    }

    public final void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public final List<?> getItems() {
        return items == null ? Collections.EMPTY_LIST : items;
    }

    public final void setItems(List<?> items) {
        this.items = items;
    }
}
