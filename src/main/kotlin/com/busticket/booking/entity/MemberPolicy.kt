package com.busticket.booking.entity

import com.busticket.booking.enum.status.CommonStatus
import javax.persistence.*

@Entity
@Table(name = "member_policies")
class MemberPolicy(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Int? = null,
        @Column(nullable = false)
        var name: String = "",
        @Column(columnDefinition = "int default 0")
        var specialRole: Int = 0,
        @Column(columnDefinition = "tinyint default 1")
        var status: Int = CommonStatus.ACTIVE.value,
        var createdAt: Long = System.currentTimeMillis(),
        var updatedAt: Long = System.currentTimeMillis(),

        @ManyToMany(cascade = [CascadeType.DETACH], fetch = FetchType.LAZY)
        @JoinTable(name = "policy_role_relations", joinColumns = [JoinColumn(name = "policy_id")], inverseJoinColumns = [JoinColumn(name = "role_id")])
        var roles: Set<MemberPolicyRole> = emptySet(),

        @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "policy")
        var members: Set<Member> = emptySet()
)