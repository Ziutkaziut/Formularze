Zad 1

Przy `CheckBox` interfejs `CompoundButton.OnCheckedChangeListener` posiada dokładnie jedną metodę (`onCheckedChanged`), dzięki czemu kompilator Java potrafi automatycznie przekształcić lambdę w ten interfejs. Natomiast interfejs `SeekBar.OnSeekBarChangeListener` posiada aż trzy metody (`onProgressChanged`, `onStartTrackingTouch`, `onStopTrackingTouch`), przez co lambda nie zadziała i wymagane jest użycie pełnej klasy anonimowej.

Zad 3

Do zachowania stanu formularza przed jego wyczyszczeniem zastosowano prywatne zmienne zapasowe w klasie `MainActivity`, które przechowują wartości pól (w tym pozycję w Spinnerze, datę, pola tekstowe oraz stan przełączników). Dzięki temu akcja "Cofnij" w Snackbarze przywraca dokładnie taki stan interfejsu, jaki użytkownik miał przed kliknięciem przycisku czyszczenia.