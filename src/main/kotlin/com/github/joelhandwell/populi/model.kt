package com.github.joelhandwell.populi

import javax.xml.bind.annotation.*

@XmlRootElement(name = "account_id")
class AccountId(var id: Int)

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
class AccessKeyResponse(
    var access_key: String,
    var account_id: AccountId,
    var account_type: String
)

@XmlRootElement(name = "specialization")
data class Specialization(
    var id: Int,
    var type: String,
    var name: String,
    var description: String,
    var abbrv: String,
    var status: String,
    var cip_code: String
)

@XmlRootElement(name = "degree")
@XmlAccessorType(XmlAccessType.FIELD)
data class Degree(
    var id: Int,
    var name: String,
    var abbrv: String,
    var program_id: Int,
    var program_name: String,
    var department_id: Int,
    var department_name: String,
    var status: String,
    var graduate: Int,
    var length: Int,
    var length_unit: String,

    @XmlElementWrapper(name = "specializations")
    @XmlElement
    var specialization: MutableList<Specialization> = mutableListOf()
)

@XmlRootElement(name = "response")
data class DegreeResponse(
    var degree: MutableList<Degree> = mutableListOf()
)

@XmlRootElement(name = "person")
data class User(
    var person_id: Int,
    var first: String,
    var last: String,
    var username: String,
    var blocked: Int
)

@XmlRootElement(name = "response")
data class UserResponse(
    var person: MutableList<User> = mutableListOf()
)

@XmlRootElement(name = "campus")
@XmlAccessorType(XmlAccessType.FIELD)
data class Campus(
    var id: Int,
    var name: String,
    var status: String,
    var city: String,
    var state: String,
    var zip: String,
    var country: String,

    @XmlElement(name = "is_primary")
    var is_primary: Int
)

@XmlRootElement(name = "response")
data class CampusResponse(
    var campus: MutableList<Campus> = mutableListOf()
)