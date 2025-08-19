package com.anpetna.item.service;

import com.anpetna.item.dto.deleteReview.DeleteReviewReq;
import com.anpetna.item.dto.deleteReview.DeleteReviewRes;
import com.anpetna.item.dto.modifyReview.ModifyReviewReq;
import com.anpetna.item.dto.modifyReview.ModifyReviewRes;
import com.anpetna.item.dto.registerItem.ReigisterItemRes;
import com.anpetna.item.dto.registerReview.RegisterReviewReq;
import com.anpetna.item.dto.searchAllReview.SearchAllReviewsReq;
import com.anpetna.item.dto.searchAllReview.SearchAllReviewsRes;
import com.anpetna.item.dto.searchOneReview.SearchOneReviewReq;
import com.anpetna.item.dto.searchOneReview.SearchOneReviewRes;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {


    @Override
    public List<SearchAllReviewsRes> getAllReviews(SearchAllReviewsReq req) {
        return List.of();
    }

    @Override
    public SearchOneReviewRes getOneReview(SearchOneReviewReq req) {
        return null;
    }

    @Override
    public ReigisterItemRes registerReview(RegisterReviewReq req) {
        return null;
    }

    @Override
    public ModifyReviewRes modifyReview(ModifyReviewReq req) {
        return null;
    }

    @Override
    public DeleteReviewRes deleteReview(DeleteReviewReq req) {
        return null;
    }
}
