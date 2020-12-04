# Bob's burgeoning fortune

#### To execute code:
* git clone https://github.com/malbera/burgeoning-fortune.git
* cd burgeoning-fortune/example
* java -jar burgeoning-fortune.jar EUR bobs_crypto.txt

#### Documentation
![](http://www.plantuml.com/plantuml/png/VP51ImCn48Nl-olcB5JlUv1gmNEn1myY4fDdMSbcbicaAk9_Dvkb51rrBqjUtlV2l5bdTQ9bZ1tUu8lYySK9hLQNT7_sG3rTFR4eWhczBh87fo7jRxZo2JtjYlT0gERwrLY5TNrqH7JdOeJUmWN83d8OF5hcHjudvQtKyr7kQUjofjT6pAprDDGtsVCuES5v_LToiT8qU_z15td3wG390wTVdxxmDinIt-TWqMUE0_y8z8rH15eokyzZMsQ8sfOL_wfgpZ5zdnPvseurcMslaK8PunS0)

java -jar burgeoning-fortune.jar $currency $file_path

- currency - to which currency you want to convert crypto
- file_path - location of wallet with crypto
    * crypto can repeat, it will be summed in the end
    * delimiter is "=" e.g BTC=123
    * if there is corrupted wallet file, exception will be thrown on REST call or reading file

