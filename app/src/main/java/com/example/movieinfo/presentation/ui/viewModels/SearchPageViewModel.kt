package com.example.movieinfo.presentation.ui.viewModels

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.movieinfo.utils.ListPagingFilterMovieCollection
import com.example.movieinfo.utils.ListPagingMovieBaseInfo
import com.example.movieinfo.utils.SearchMovieFilterImpl
import com.example.movieinfo.utils.convert
import com.movieinfo.domain.entity.MovieCollection
import com.movieinfo.domain.usecase.SearchPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchPageViewModel @Inject constructor(private val useCase: SearchPageUseCase) :
    ViewModel() {

    var movieTypeInd = mutableIntStateOf(0)
    var sortTypeIndex = mutableIntStateOf(0)
    var yearRange = MutableStateFlow(Pair(1998, 2024))
    val yearBefore = mutableIntStateOf(2024)
    val yearAfter = mutableIntStateOf(1998)
    var ratingRange = MutableStateFlow((1f..10f))
    val queryState = TextFieldState("")
    val filterMovie = mutableStateOf(SearchMovieFilterImpl())
    val queryCountry = TextFieldState("")
    val queryGenre = TextFieldState("")

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val movieSearchFilterResult = snapshotFlow { filterMovie.value }
        .debounce(3000).distinctUntilChanged { old, new ->
            Timber.d("old $old new $new")
            old == new
        }.flatMapLatest { _ ->
            Pager(
                PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false
                )
            ) {
                ListPagingFilterMovieCollection(this)
            }.flow.cachedIn(viewModelScope)
        }


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val movieSearchResult = snapshotFlow { queryState.text }.debounce(1000).distinctUntilChanged()
        .flatMapLatest { query ->
            Pager(
                PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false
                )
            ) {
                ListPagingMovieBaseInfo(
                    this,
                    query.toString()
                )
            }.flow.cachedIn(viewModelScope)
        }




    suspend fun searchByFilter(
        page: Int = 1
    ): List<MovieCollection> {

        Timber.d("search by query ${queryState.text}")
        return viewModelScope.async(Dispatchers.IO) {
            useCase.execute(
                SearchMovieFilterImpl(
                    arrayOf(countriesToId[filterMovie.value.countryInd.first()].first),
                    arrayOf(genresToId[filterMovie.value.genreInd.first()].first),
                    filterMovie.value.sortType,
                    filterMovie.value.movieType,
                    filterMovie.value.raitingFrom,
                    filterMovie.value.raitingTo,
                    filterMovie.value.yearAfter,
                    filterMovie.value.yearBefore,
                    queryState.text.toString()
                ),
                page
            )
        }.await()
    }

    suspend fun searchByKeyWordPaging(query: String, page: Int = 1): List<MovieCollection> {
        return viewModelScope.async(Dispatchers.IO) {

            useCase.execute(query, page)
        }.await().map { it.convert() }
    }

    val mainSearchTabList = listOf("Все", "Фильмы", "Сериалы")
    val mainSortTabList = listOf("Дата", "Популярность", "Рейтинг")
    val genresToId = listOf(
        1 to "триллер",
        2 to "драма",
        3 to "криминал",
        4 to "мелодрама",
        5 to "детектив",
        6 to "фантастика",
        7 to "приключения",
        8 to "биография",
        9 to "фильм-нуар",
        10 to "вестерн",
        11 to "боевик",
        12 to "фэнтези",
        13 to "комедия",
        14 to "военный",
        15 to "история",
        16 to "музыка",
        17 to "ужасы",
        18 to "мультфильм",
        19 to "семейный",
        20 to "мюзикл",
        21 to "спорт",
        22 to "документальный",
        23 to "короткометражка",
        24 to "аниме",
        25 to "",
        26 to "новости",
        27 to "концерт",
        28 to "для взрослых",
        29 to "церемония",
        30 to "реальное ТВ",
        31 to "игра",
        32 to "ток-шоу",
        33 to "детский"
    )
    val countriesToId = listOf(
        1 to "США",
        2 to "Швейцария",
        3 to "Франция",
        4 to "Польша",
        5 to "Великобритания",
        6 to "Швеция",
        7 to "Индия",
        8 to "Испания",
        9 to "Германия",
        10 to "Италия",
        11 to
                "Гонконг",
        12 to
                "Германия (ФРГ)",
        13 to
                "Австралия",
        14 to
                "Канада",
        15 to
                "Мексика",
        16 to
                "Япония",
        17 to
                "Дания",
        18 to
                "Чехия",
        19 to "Ирландия",
        20 to "Люксембург",
        21 to "Китай",
        22 to "Норвегия",
        23 to "Нидерланды",
        24 to "Аргентина",
        25 to "Финляндия",
        26 to "Босния и Герцеговина",
        27 to "Австрия",
        28 to "Тайвань",
        29 to "Новая Зеландия",
        30 to "Бразилия",
        31 to "Чехословакия",
        32 to "Мальта",
        33 to "СССР",
        34 to "Россия",
        35 to "Югославия",
        36 to "Португалия",
        37 to "Румыния",
        38 to "Хорватия",
        39 to "ЮАР",
        40 to "Куба",
        41 to "Колумбия",
        42 to "Израиль",
        43 to "Намибия",
        44 to "Турция",
        45 to "Бельгия",
        46 to "Сальвадор",
        47 to "Исландия",
        48 to "Венгрия",
        49 to "Корея Южная",
        50 to "Лихтенштейн",
        51 to "Болгария",
        52 to "Филиппины",
        53 to "Доминикана",
        54 to "",
        55 to "Марокко",
        56 to "Таиланд",
        57 to "Кения",
        58 to "Пакистан",
        59 to "Иран",
        60 to "Панама",
        61 to "Аруба",
        62 to "Ямайка",
        63 to "Греция",
        64 to "Тунис",
        65 to "Кыргызстан",
        66 to "Пуэрто Рико",
        67 to "Казахстан",
        68 to "Югославия (ФР)",
        69 to "Алжир",
        70 to "Германия (ГДР)",
        71 to "Сингапур",
        72 to "Словакия",
        73 to "Афганистан",
        74 to "Индонезия",
        75 to "Перу",
        76 to "Бермуды",
        77 to "Монако",
        78 to "Зимбабве",
        79 to "Вьетнам",
        80 to "Антильские Острова",
        81 to "Саудовская Аравия",
        82 to "Танзания",
        83 to "Ливия",
        84 to "Ливан",
        85 to "Кувейт",
        86 to "Египет",
        87 to "Литва",
        88 to "Венесуэла",
        89 to "Словения",
        90 to "Чили",
        91 to "Багамы",
        92 to "Эквадор",
        93 to "Коста-Рика",
        94 to "Кипр",
        95 to "Уругвай",
        96 to "Ирак",
        97 to "Мартиника",
        98 to "Эстония",
        99 to "ОАЭ",
        100 to "Бангладеш",
        101 to "Македония",
        102 to "Гвинея",
        103 to "Иордания",
        104 to "Латвия",
        105 to "Армения",
        106 to "Украина",
        107 to "Сирия",
        108 to "Шри-Ланка",
        109 to "Нигерия",
        110 to "Берег Слоновой кости",
        111 to "Грузия",
        112 to "Сенегал",
        113 to "Монголия",
        114 to "Габон",
        115 to "Замбия",
        116 to "Албания",
        117 to "Камерун",
        118 to "Буркина-Фасо",
        119 to "Узбекистан",
        120 to "Малайзия",
        121 to "Сербия",
        122 to "Гана",
        123 to "Таджикистан",
        124 to "Гаити",
        125 to "Конго (ДРК)",
        126 to "Гватемала",
        127 to "Российская империя",
        128 to "Беларусь",
        129 to "Молдова",
        130 to "Азербайджан",
        131 to "Палестина",
        132 to "Оккупированная Палестинская территория",
        133 to "Корея Северная",
        134 to "Никарагуа",
        135 to "Камбоджа",
        136 to "Ангола",
        137 to "Сербия и Черногория",
        138 to "Непал",
        139 to "Бенин",
        140 to "Гваделупа",
        141 to "Гренландия",
        142 to "Гвинея-Бисау",
        143 to "Макао",
        144 to "Парагвай",
        145 to "Мавритания",
        146 to "Руанда",
        147 to "Фарерские острова",
        148 to "Кот-д’Ивуар",
        149 to "Гибралтар",
        150 to "Ботсвана",
        151 to "Боливия",
        152 to "Мадагаскар",
        153 to "Кабо-Верде",
        154 to "Чад",
        155 to "Мали",
        156 to "Фиджи",
        157 to "Бутан",
        158 to "Барбадос",
        159 to "Тринидад и Тобаго",
        160 to "Мозамбик",
        161 to "Заир",
        162 to "Андорра",
        163 to "Туркменистан",
        164 to "Гайана",
        165 to "Корея",
        166 to "Нигер",
        167 to "Конго",
        168 to "Того",
        169 to "Ватикан",
        170 to "Черногория",
        171 to "Бурунди",
        172 to "Папуа - Новая Гвинея",
        173 to "Бахрейн",
        174 to "Гондурас",
        175 to "Судан",
        176 to "Эфиопия",
        177 to "Йемен",
        178 to "Вьетнам Северный",
        179 to "Суринам",
        180 to "Маврикий",
        181 to "Белиз",
        182 to "Либерия",
        183 to "Лесото",
        184 to "Уганда",
        185 to "Каймановы острова",
        186 to "Антигуа и Барбуда",
        187 to "Западная Сахара",
        188 to "Сан-Марино",
        189 to "Гуам",
        190 to "Косово",
        191 to "Лаос",
        192 to "Катар",
        193 to "Оман",
        194 to "Американские Виргинские острова",
        195 to "Сиам",
        196 to "Сьерра-Леоне",
        197 to "Эритрея",
        198 to "Сомали",
        199 to "Доминика",
        200 to "Бирма",
        201 to "Реюньон",
        202 to "Федеративные Штаты Микронезии",
        203 to "Самоа",
        204 to "Американское Самоа",
        205 to "Свазиленд",
        206 to "Французская Полинезия",
        207 to "Мьянма",
        208 to "Новая Каледония",
        209 to "Французская Гвиана",
        210 to "Сент-Винсент и Гренадины",
        211 to "Малави",
        212 to "Экваториальная Гвинея",
        213 to "Коморы",
        214 to "Кирибати",
        215 to "Тувалу",
        216 to "Тимор-Лесте",
        217 to "ЦАР",
        218 to "Тонга",
        219 to "Гренада",
        220 to "Гамбия",
        221 to "Антарктида",
        222 to "Острова Кука",
        223 to "Остров Мэн",
        224 to "Внешние малые острова США",
        225 to "Монтсеррат",
        226 to "Маршалловы острова",
        227 to "Бруней-Даруссалам",
        228 to "Сейшельские острова",
        229 to "Палау",
        230 to "Сент-Люсия",
        231 to "Вануату",
        232 to "Мальдивы",
        233 to "Босния",
        234 to "Уоллис и Футуна",
        235 to "Белоруссия",
        236 to "Киргизия",
        239 to "Джибути",
        240 to "Виргинские Острова (США)",
        241 to "Северная Македония",
        242 to "Виргинские Острова (Великобритания)",
        3545269 to "Сент-Люсия ",
        3781461 to "Сент-Китс и Невис",
        3985922 to "Соломоновы Острова",
        4336645 to "Виргинские Острова",
        7801402 to "Фолклендские острова",
        10842163 to "Остров Святой Елены",
        32518739 to "острова Теркс и Кайкос",
        47738117 to "Мелкие отдаленные острова США",
        65870322 to "Сан-Томе и Принсипи",
        100999433 to "Эсватини"
    )

    companion object {
        fun provideFactory(
            useCase: SearchPageUseCase
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchPageViewModel(useCase) as T
            }
        }
    }
}