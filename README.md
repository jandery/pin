# pin

The purpose of this project is to validate Personal identification numbers / Social security numbers

## Usage

```
val personalIdentification = SwedishPersonalIdentification.create("197306195640")
val isValid = personalIdentification.isValid
val formattedSSN = personalIdentification.getFormatted()

/* How to generate random and valid numbers */
val randomIdentificationNumberFromLocalDate = SwedishPersonalIdentification.generate(LocalDate.now())
val randomIdentificationNumberFromYear = SwedishPersonalIdentification.generate(Year.now())
```

