@testScreen
Feature: Test impl - Chrome


  @Video @tmsLink=OAT-219 @severity=blocker @issue=BUG-12312 @known @muted @goofy=dog @melted @link=http://yandex.ru
  Scenario: Chrome Buy latest console camera and pre order game
    Given I'm using browser "firefox"
    And Navigate to url "http://www.google.com"
  And Page has title "hello world"
#    And Login to Amazon with "hackathon.workable@gmail.com" username and "kwdikos1234!" password
#    And Search for "PlayStation 4 Slim 1TB Console" product
#    And Select first product in results
#    And Product should be "In Stock"
#    And Merchant should be verified by "Amazon.com"
#    And Add Item to shopping cart
#    And Search for "PlayStation 4 camera" product
#    And Select first product in results¬
#    And Product should be "In Stock"
#    And Add Item to shopping cart
#    And Search for "just dance 2018 playstation 4" product
#    And Select first product in results
#    And Add Item to shopping cart
#    And View shopping cart
#    And Proceed to checkout