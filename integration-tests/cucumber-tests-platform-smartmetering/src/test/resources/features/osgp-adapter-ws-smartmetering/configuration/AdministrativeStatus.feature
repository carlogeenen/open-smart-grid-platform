@SmartMetering @Platform @SmartMeteringConfiguration
Feature: SmartMetering Configuration - Administrative Status
  As a grid operator
  I want to be able to perform get or set the administrative status on a device
  So that it can be kept in sync with the wishes of the houshold the device is used

  #Background: 
    #Given a dlms device
      #| DeviceIdentification | TEST1024000000001 |
      #| DeviceType           | SMART_METER_E     |

  Scenario: Retrieve get administrative status from a device
    When the get administrative status request is received
      | DeviceIdentification | TEST1024000000001 |
    Then the administrative status should be returned
      | DeviceIdentification | TEST1024000000001 |

  Scenario: Set administrative status on a device
    When the set administrative status request is received
      | DeviceIdentification     | TEST1024000000001 |
      | AdministrativeStatusType | ON                |
    Then the administrative status should be set on the device
      | DeviceIdentification | TEST1024000000001 |
@P1
  Scenario: Set administrative text
    When the set administrative status request is received
      | DeviceIdentification     | E0026000026406115              |
      | AdministrativeStatusType | ON                             |
      | Message                  | test P1 message voor server |
    Then the administrative status should be set on the device
      | DeviceIdentification | E0026000026406115 |
