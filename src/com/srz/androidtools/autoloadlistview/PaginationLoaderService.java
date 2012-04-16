package com.srz.androidtools.autoloadlistview;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 5/16/11
 * Time: 11:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PaginationLoaderService {
    List load(int page) throws NoSuchPageException;
}
