package com.dk.services.model

data class Service (var id:String, var name:String)

data class Country (var id:String, var name:String, var code:String)

data class CountryService (var id:String, var service: Service, var country: Country)

data class City(var id:String, var name:String, var code:String, var country: Country)



data class User(var id:String, var name:String)

data class Role(var id:String, var name:String)

data class UserRole(var id:String, var name:String)



data class CountryServiceRole(var id:String, var countryService: CountryService, var role: Role)

