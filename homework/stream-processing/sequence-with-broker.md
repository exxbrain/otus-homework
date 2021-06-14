```mermaid
sequenceDiagram
    participant User
    participant Orders
    participant Billing
    participant Broker
    participant Notification
    participant Identity
    rect rgba(0, 0, 0, .2)
        User->>Identity: Register
        activate Identity
            Identity->>Broker: User created
        deactivate Identity
        activate Broker
            Broker->>Billing: User created
        deactivate Broker
    end
    User->>+Billing: Put money
    Billing-->>-User: Ok 
    
    User->>+Orders: Create order
    Orders-->>-User: Ok
    
    User->>Orders: Confirm order
    activate Orders
    Orders->>Broker: Order confirmed
    activate Broker
    Orders-->>User: Ok
    deactivate Orders
    Broker->>Billing: Order confirmed
    deactivate Broker
    activate Billing
    Billing->>Billing: Validate order
    Billing->>Broker: Order approved
    deactivate Billing
    activate Broker
    Broker->>Notification: Order approved
    Broker->>Orders: Order approved
    deactivate Broker
    
    User->>Billing: Purchase order
    activate Billing
        Billing->>Billing: Validate order
        Billing->>Billing: Charge money
        Billing->>+Broker: Order purchased
        Broker->>-Notification: Order purchased
        Billing-->>User: Ok
    deactivate Billing
```