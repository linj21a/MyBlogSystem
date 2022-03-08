/**
 * @author 60417
 * @date 2022/2/21
 * @time 13:34
 * @todo
 */
package com.yuyefanhua.blogsystem.service.impl;

import com.yuyefanhua.blogsystem.dao.ViewsDao;
import com.yuyefanhua.blogsystem.service.ViewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ViewsServiceImpl implements ViewsService {
    @Autowired
    private ViewsDao viewsDao;
    @Override
    public int getAllViewsForPeople() {
        return viewsDao.getAllViewsForPeople();
    }
    @Override
    public int getYesterdayViewsForPeople() {
        return viewsDao.getYesterdayViewsForPeople();
    }

    @Override
    public int getAWeekViewsForPeople() {
        return viewsDao.getAWeekViewsForPeople();
    }

    @Override
    public int getAMonthViewsForPeople() {
        return viewsDao.getAMonthViewsForPeople();
    }
    @Override
    public void updateViewsForPeople() {
        viewsDao.updateViewsForPeople();
    }
}
