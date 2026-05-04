package org.melongames.dailychallenge.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.melongames.dailychallenge.ChallengeRepository
import org.melongames.dailychallenge.MainViewModel

val appModule = module {
    singleOf(::ChallengeRepository)
    factoryOf(::MainViewModel)
}