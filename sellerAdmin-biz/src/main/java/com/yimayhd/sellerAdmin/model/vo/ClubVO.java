package com.yimayhd.sellerAdmin.model.vo;

import com.yimayhd.sellerAdmin.model.Club;
import com.yimayhd.sellerAdmin.model.query.ClubListQuery;

/**
 * Created by Administrator on 2015/11/2.
 */
public class ClubVO {
    private Club club;
    private ClubListQuery clubListQuery;
    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public ClubListQuery getClubListQuery() {
        return clubListQuery;
    }

    public void setClubListQuery(ClubListQuery clubListQuery) {
        this.clubListQuery = clubListQuery;
    }

}
