package org.example.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.example.model.Account
import java.time.OffsetDateTime

data class AccountDto(
    @JsonProperty("id") var id: Long?,
    @JsonProperty("username") var username: String = "",
    @JsonProperty("password") var password: String = "",
    @JsonProperty("createdAt") var createdAt: OffsetDateTime,
    @JsonProperty("updatedAt") var updatedAt: OffsetDateTime,

) {
    companion object {
        fun toDto(accountDto: AccountDto) =
            Account()
                .apply {
                    username = accountDto.username
                    password = accountDto.password
                    createdAt = accountDto.createdAt
                    updatedAt = accountDto.updatedAt

                }

        fun fromDto(account: Account) =
            AccountDto(
                id = account.id,
                username = account.username,
                password = account.password,
                createdAt = account.createdAt,
                updatedAt = account.updatedAt,
            )
    }
}