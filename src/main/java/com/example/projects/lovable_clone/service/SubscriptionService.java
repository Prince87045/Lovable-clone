package com.example.projects.lovable_clone.service;

import com.example.projects.lovable_clone.dto.subscription.CheckoutRequest;
import com.example.projects.lovable_clone.dto.subscription.CheckoutResponse;
import com.example.projects.lovable_clone.dto.subscription.SubscriptionResponse;
import org.jspecify.annotations.Nullable;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription(Long userId);

    CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal();
}
