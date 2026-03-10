```mermaid
classDiagram
    class StationType {
        <<enumeration>>
        KITCHEN
        BAR
        PIZZA
    }
    
    class TicketPrinter {
        <<interface>>
        +printTicket(OrderItem) String
    }
    
    class TicketPrinterFactory {
        <<component>>
        +create(StationType) TicketPrinter
    }
    
    class KitchenTicketPrinter {
        +printTicket(OrderItem) String
    }
    
    class BarTicketPrinter {
        +printTicket(OrderItem) String
    }
    
    class PizzaTicketPrinter {
        +printTicket(OrderItem) String
    }
    
    class Order {
        -id: Long
        -orderNumber: String
        -status: OrderStatus
        -items: List~OrderItem~
    }
    
    class OrderItem {
        -id: Long
        -quantity: Integer
        -unitPrice: BigDecimal
        -notes: String
        -extras: String
        -stationType: StationType
        +getOrder() Order
        +getMenuItem() MenuItem
    }
    
    class MenuItem {
        -id: Long
        -name: String
        -price: BigDecimal
        -stationType: StationType
    }
    
    TicketPrinterFactory --> StationType : uses
    TicketPrinterFactory --> TicketPrinter : creates
    TicketPrinterFactory --> KitchenTicketPrinter : instantiates
    TicketPrinterFactory --> BarTicketPrinter : instantiates
    TicketPrinterFactory --> PizzaTicketPrinter : instantiates
    
    KitchenTicketPrinter ..|> TicketPrinter
    BarTicketPrinter ..|> TicketPrinter
    PizzaTicketPrinter ..|> TicketPrinter
    
    TicketPrinter --> OrderItem : uses
    OrderItem --> Order : belongs to
    OrderItem --> MenuItem : references
    Order --> StationType : has
```

## Flux de execuție Factory Method:

```mermaid
sequenceDiagram
    participant Controller
    participant Factory as TicketPrinterFactory
    participant Printer as TicketPrinter
    participant Item as OrderItem
    
    Controller->>Item: getStationType()
    Item-->>Controller: StationType.KITCHEN
    
    Controller->>Factory: create(StationType.KITCHEN)
    
    alt KITCHEN type
        Factory->>Printer: new KitchenTicketPrinter()
    else BAR type
        Factory->>Printer: new BarTicketPrinter()
    else PIZZA type
        Factory->>Printer: new PizzaTicketPrinter()
    end
    
    Factory-->>Controller: TicketPrinter instance
    
    Controller->>Printer: printTicket(OrderItem)
    Printer->>Item: getOrder()
    Printer->>Item: getMenuItem()
    Printer->>Item: getQuantity()
    Printer->>Item: getNotes()
    Printer->>Item: getExtras()
    Printer-->>Controller: formatted ticket String
    
    Controller->>Controller: send to printer/display
```

## Comparație: Factory Method vs Abstract Factory

### Factory Method Pattern (TicketPrinterFactory)
```
Responsabilitate: Creează UN singur tip de obiect
Interfață: TicketPrinter
Implementări: KitchenTicketPrinter, BarTicketPrinter, PizzaTicketPrinter
Parametru: StationType enum
Avantaj: Decuplare client de clasele concrete
```

### Abstract Factory Pattern (RestaurantModeFactory)
```
Responsabilitate: Creează FAMILII de obiecte corelate
Interfețe: OrderFormConfig + ReceiptConfig
Implementări: Standard mode, Banquet mode
Avantaj: Coeziune puternică între produsele din aceeași familie
```

