# Factory Method Pattern - UML Diagram

## Overview
This document describes the Factory Method pattern implementation for the TicketPrinter system in the Restaurant POS application.

## Pattern Description
The **Factory Method Pattern** is a creational design pattern that provides an interface for creating objects without specifying their exact class. The factory decides which class to instantiate based on input parameters.

## Implementation in Restaurant POS

### Components

1. **TicketPrinter (Interface)** - Product Interface
   - Defines the contract for all ticket printers
   - Method: `printTicket(OrderItem item): String`

2. **TicketPrinterFactory (Factory)** - Factory Class
   - Responsible for creating the correct `TicketPrinter` instance
   - Method: `create(StationType stationType): TicketPrinter`
   - Uses Spring `@Component` annotation

3. **Concrete Implementations** - Concrete Products
   - **KitchenTicketPrinter** - Formats tickets for kitchen station
   - **BarTicketPrinter** - Formats tickets for bar station  
   - **PizzaTicketPrinter** - Formats tickets for pizza station

4. **StationType (Enum)** - Factory Parameter
   - KITCHEN
   - BAR
   - PIZZA

## Class Diagram

```
┌─────────────────────────────┐
│   <<interface>>             │
│   TicketPrinter             │
├─────────────────────────────┤
│ + printTicket(OrderItem)    │
│   : String                  │
└──────────▲──────────────────┘
           │
           │ implements
           │
    ┌──────┴──────┬──────────────────┐
    │             │                  │
┌───┴──────────┐ ┌┴───────────────┐ ┌┴────────────────┐
│ Kitchen      │ │ Bar            │ │ Pizza           │
│ TicketPrinter│ │ TicketPrinter  │ │ TicketPrinter   │
├──────────────┤ ├────────────────┤ ├─────────────────┤
│+ printTicket │ │+ printTicket   │ │+ printTicket    │
└──────────────┘ └────────────────┘ └─────────────────┘
       ▲                ▲                    ▲
       │                │                    │
       └────────────────┼────────────────────┘
                        │ creates
              ┌─────────┴──────────┐
              │ TicketPrinter      │
              │ Factory            │
              ├────────────────────┤
              │ + create(          │
              │   StationType)     │
              │   : TicketPrinter  │
              └────────────────────┘
                        │
                        │ uses
                        ▼
              ┌────────────────────┐
              │ <<enumeration>>    │
              │ StationType        │
              ├────────────────────┤
              │ KITCHEN            │
              │ BAR                │
              │ PIZZA              │
              └────────────────────┘
```

## How It Works

1. **Client Request**: A service needs to print a ticket for a specific station type
2. **Factory Call**: Client calls `factory.create(StationType.KITCHEN)`
3. **Object Creation**: Factory instantiates the appropriate concrete class
4. **Return**: Factory returns the object as `TicketPrinter` interface type
5. **Usage**: Client calls `printTicket(orderItem)` on the returned object

## Code Example

```java
// Factory creates the right printer
TicketPrinter printer = ticketPrinterFactory.create(StationType.KITCHEN);

// Client uses the printer without knowing the concrete type
String ticket = printer.printTicket(orderItem);
// Output: "[KITCHEN TICKET] Table 5 | Item: Pasta x2 | Notes: No garlic"
```

## Benefits

1. **Encapsulation**: Object creation logic is centralized in the factory
2. **Flexibility**: Easy to add new station types without changing client code
3. **Loose Coupling**: Client depends on interface, not concrete classes
4. **Single Responsibility**: Each printer handles its own formatting logic

## UML Files

- **PlantUML Diagram**: `factory-pattern-uml.puml` - Can be rendered with PlantUML tools
- **Documentation**: This file

## Rendering the PlantUML Diagram

To render the PlantUML diagram, you can:
1. Use online tools like [PlantUML Web Server](http://www.plantuml.com/plantuml/)
2. Use IDE plugins (IntelliJ IDEA PlantUML Integration, VS Code PlantUML extension)
3. Use command-line PlantUML: `java -jar plantuml.jar factory-pattern-uml.puml`

