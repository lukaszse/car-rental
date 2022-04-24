# 1. Opis wybranych algorytmów

## 1.2. Algorytm sprawdzania dostępności samochodu w danym przedziale czasu

W aplikacji zastosowano mechanizm sprawdzania dostępności samochodu w danym przedziale czasu.
Algorytm ten wykorzystywany jest w dwóch sytuacjach:
1. Wyszykiwanie samochodów w widoku **cars** - użytkownik ma możliwoś wpisania zakresu czasu (`dateFrom` oraz `dateTo`), w celu wyszukiwania dostępnych do wypożyczenia samochodów. Mechanizm ten realizowany jest z wykorzystaniem zapytania REST API **@GET** które zaimplementowane z wykorzystaniem Javascript.
Do obsługi zapytania **@GEY** stworzon endpoint `cars/findCars` który przyjmuje m.in. dwa parametry QueryParam - dateFrom oraz DateTo.
2. Rezerwowanie samochodu (dodawanie rezerwacji) - po wyszukaniu samochodu użytkownim w widoku **cars** ma możliwość zarezrwowania wybranego samochodu po przez kliknięcie przycisku **Book**, a następnie po przez potwierdzenie zamówienia po przez kliknięcie przycisku **Submit** na ekranie z z danymi samochodu, co spowoduje wysłanie zapytania **@POST** oraz wywołanie metody 'processAddReservation' która przyjmuje kilka paramerów w tym parametry `dateFrom` oraz `dateTo`.
Przed zarezerwowaniem samochodu również sprawdzana jest dostępność samochodu, a operacja przeprowadzana jest w formie transakcji.

Obie wyżej wymienione metody korzystają z klasy `AvailabilityService` oraz zaimlementowanego w niej algorytmu:

```java
    public boolean isCarAvailable(final Integer carId, final TimePeriod timePeriod) {
        if(timePeriod.getDateFrom() == null && timePeriod.getDateTo() == null) {
            return true;
        }
        return reservationSearchRepository.findByCar_Id(carId).stream()
                .map(reservation -> TimePeriod.of(reservation.getDateFrom(), reservation.getDateTo()))
                .noneMatch(reservationPeriod -> checkIfPeriodOverlap(timePeriod, reservationPeriod));
    }

    private static boolean checkIfPeriodOverlap(final TimePeriod timePeriod1, TimePeriod timePeriod2) {
        final LocalDate s1 = timePeriod1.getDateFrom();
        final LocalDate e1 = timePeriod1.getDateTo();
        final LocalDate s2 = timePeriod2.getDateFrom();
        final LocalDate e2 = timePeriod2.getDateTo();

        if(s1 == null || e1 == null || s2 == null || e2 == null) {
            return false;
        }

        if(s1.compareTo(s2)<0 && e1.compareTo(s2)>0 ||
                s1.compareTo(e2)<0 && e1.compareTo(e2)>0 ||
                s1.compareTo(s2)<0 && e1.compareTo(e2)>0 ||
                s1.compareTo(s2)>0 && e1.compareTo(e2)<0 )
        {
            log.info("Periods overlap! Period 1: {}, Period2: {}", timePeriod1, timePeriod2);
            return true;
        }
        else {
            return false;
        }
    }

```

Jak jak widać (co wynika z powyższego kodu) metoda `isCarAvailable` wywołuje zapytanie bazy danych z wykorzystaniem 'RepositorySearchService` w celu pobrania wszystkich rezerwacji dla danego samochodu, a następnie sprawcza czy jakikolwiek okres z pobranych rezerwacji nie pokrywa się z okresem rezerwacji wymaganym przez użytkownika.
Jeśli żaden z tych okresów się nie pokrywa `noneMatch` metoda zwraca `true`, w przeciwnym razie `false`.
Samo sprawdzenie czy pojedynczy pobrany z bazy danych okres rezerwacji pokrywa się z okresem rezerwacji wymaganym przez użytkownika sprawdzane jest w metodzie 'checkIfPeriodOverlap'.


## 1.2. Schemat blokowy algorytmu
