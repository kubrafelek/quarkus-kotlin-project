package org.example.exception

import io.quarkus.security.ForbiddenException

class InvalidPasswordException : ForbiddenException("Invalid password")