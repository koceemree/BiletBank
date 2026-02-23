@registration
# Feature: BiletBank Acente Kayıt İşlemleri
Feature: BiletBank Agency Registration Process

  Background: Runner
   # Kullanıcı BiletBank giriş sayfasındadır
    Given The user is on the BiletBank login page
    # Kullanıcı Yeni Acente Üyelik butonuna tıklar
    When The user clicks on the new agent registration button
     # Kullanıcının sözleşme sayfasına yönlendirildiği doğrulanır
    Then The user should be redirected to the contract page


  # Senaryo: Geçerli bilgilerle yeni acente kaydı oluşturulabilmeli
  Scenario: A new agency registration should be created successfully with valid information
        # Kullanıcı sözleşme onay kutusunu işaretler
    When The user selects the contract checkbox
    # Kullanıcı Devam Et butonuna tıklar
    And The user clicks the continue button
    # Kullanıcının kayıt form sayfasına yönlendirildiği doğrulanır
    Then The user should be redirected to the registration form page
    # Kullanıcı kayıt formunu geçerli parametrik verilerle doldurur (Örn: "TEST-EMREKOC-01")
    When The user fills out the registration form with valid parametric data
    # Kullanıcı acente faaliyetleri için gerekli onay kutularını seçer (Örn: "Uçak", "Otel")
    And The user selects the required checkboxes for agency activities
    # Kullanıcı kaydet butonuna tıklar
    And The user clicks the save button
    # Kullanıcı başarılı kayıt mesajını doğrular
    Then The user should verify the successful registration message



  # Senaryo: Sözleşme onaylanmadan Devam Et butonuna basıldığında sistem uyarı vermeli
  Scenario: The system should display a warning when trying to proceed without accepting the contract
    # Kullanıcı sözleşme sayfasındadır (Login sayfasından geçiş yapıldıktan sonra)
    Given The user is on the contract page
    # Kullanıcı sözleşme onay kutusunu boş bırakır
    When The user leaves the contract checkbox unchecked
    # Kullanıcı Devam Et butonuna tıklar
    And The user clicks the continue button
    # Sistem sözleşme onayı için hata mesajı göstermelidir (veya alan kızarmalıdır)
    Then The system should display a validation error for the contract
    # Kullanıcının kayıt form sayfasına yönlendirilmediği doğrulanır
    And The user should not be redirected to the registration form page


  # Senaryo: Zorunlu alanlar boş bırakıldığında sistem uyarı vermeli ve kayıt yapmamalı
  Scenario: The system should display a warning and prevent registration when mandatory fields are left empty
    # Kullanıcı sözleşmeyi kabul ettikten sonra doğrudan kayıt form sayfasındadır
    Given The user is on the registration form page directly after accepting the contract
    # Kullanıcı acente adı ve mail alanlarındaki karakter sınırlarını doğrular
    Then the user verifies character limits for agency name and mail
 # Kullanıcı zorunlu alanları boş bırakır (Örn: "Acente Adı", "Mail Adresi")
    When The user leaves mandatory fields empty
    # Kullanıcı Kaydet butonuna tıklar
    And The user clicks the save button
    # Kullanıcı zorunlu alanların altında doğrulama hata mesajlarını görmelidir
    Then The user should see validation error messages under the required fields
# Zorunlu alanların 'invalid' class'ı ile işaretlendiği (kırmızı olduğu) doğrulanır
    And the user verifies that mandatory fields are highlighted as invalid
# Kullanıcı geçersiz bir e-posta formatı girer
    When the user enters an invalid email format
    # Kullanıcı geçersiz e-posta formatı uyarısını görmelidir
    Then the user should see an invalid email format warning
    # Kayıt işleminin tamamlanmadığı doğrulanır
    And The registration process should not be completed
