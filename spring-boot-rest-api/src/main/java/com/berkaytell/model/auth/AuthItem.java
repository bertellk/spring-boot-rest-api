package com.berkaytell.model.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "auth_items")
public class AuthItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "auth_action_id")
    private AuthAction authAction;

    @OneToOne
    @JoinColumn(name = "endpoint_id")
    private Endpoint endpoint;

    @OneToOne
    @JoinColumn(name = "auth_page_id")
    private AuthPage authPage;

}
