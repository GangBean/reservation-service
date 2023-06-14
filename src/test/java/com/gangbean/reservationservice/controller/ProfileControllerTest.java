package com.gangbean.reservationservice.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class ProfileControllerTest {

    @Test
    public void prod_profile_조회됨() {
        String expected = "prod";
        MockEnvironment mockEnvironment = new MockEnvironment();
        mockEnvironment.addActiveProfile(expected);
        mockEnvironment.addActiveProfile("test-db");

        assertThat(new ProfileController(mockEnvironment).profile()).isEqualTo(expected);
    }

    @Test
    public void prod_profile_없으면_첫_번째가_조회됨() {
        String expected = "test-db";
        MockEnvironment mockEnvironment = new MockEnvironment();
        mockEnvironment.addActiveProfile(expected);

        assertThat(new ProfileController(mockEnvironment).profile()).isEqualTo(expected);
    }

    @Test
    public void active_profile_없으면_default_조회됨() {
        String expected = "default";
        MockEnvironment mockEnvironment = new MockEnvironment();

        assertThat(new ProfileController(mockEnvironment).profile()).isEqualTo(expected);
    }

}