# 🎓 GHID COMPLET - DESIGN PATTERNS ÎN RESTAURANT POS

## 📖 Cuprins
1. [Factory Method Pattern](#factory-method-pattern)
2. [Abstract Factory Pattern](#abstract-factory-pattern)
3. [Facade Pattern](#facade-pattern)
4. [Comparații și Diferențe](#comparații)
5. [FAQ](#faq)

---

## Factory Method Pattern

### 🎯 Ce este?
**Factory Method** este un pattern creațional care definește o interfață pentru crearea obiectelor, dar permite subclaselor să decidă ce clasă să instantieze.

### 📍 Locație în Proiect
```
src/main/java/com/restaurant/pos/patterns/factory/
├── TicketPrinter.java (interface)
├── TicketPrinterFactory.java (factory)
├── KitchenTicketPrinter.java (concrete product)
├── BarTicketPrinter.java (concrete product)
└── PizzaTicketPrinter.java (concrete product)
```

### 🔧 Componentele

#### 1. **TicketPrinter** (Product Interface)
```java
public interface TicketPrinter {
    String printTicket(OrderItem item);
}
```
Defineşte contractul pentru toţi printerele.

#### 2. **TicketPrinterFactory** (Factory)
```java
@Component
public class TicketPrinterFactory {
    public TicketPrinter create(StationType stationType) {
        return switch (stationType) {
            case KITCHEN -> new KitchenTicketPrinter();
            case BAR     -> new BarTicketPrinter();
            case PIZZA   -> new PizzaTicketPrinter();
        };
    }
}
```
Responsabil pentru crearea instanțelor corecte.

#### 3. **Concrete Implementations**

**KitchenTicketPrinter:**
```java
public class KitchenTicketPrinter implements TicketPrinter {
    @Override
    public String printTicket(OrderItem item) {
        return String.format(
            "[KITCHEN TICKET] Table %d | Item: %s x%d | Notes: %s",
            item.getOrder().getTable().getTableNumber(),
            item.getMenuItem().getName(),
            item.getQuantity(),
            item.getNotes() != null ? item.getNotes() : "—"
        );
    }
}
```

**BarTicketPrinter:**
```java
public class BarTicketPrinter implements TicketPrinter {
    @Override
    public String printTicket(OrderItem item) {
        return String.format(
            "[BAR TICKET] Table %d | Drink: %s x%d | Notes: %s",
            item.getOrder().getTable().getTableNumber(),
            item.getMenuItem().getName(),
            item.getQuantity(),
            item.getNotes() != null ? item.getNotes() : "—"
        );
    }
}
```

**PizzaTicketPrinter:**
```java
public class PizzaTicketPrinter implements TicketPrinter {
    @Override
    public String printTicket(OrderItem item) {
        return String.format(
            "[PIZZA TICKET] Table %d | Pizza: %s x%d | Extras: %s | Notes: %s",
            item.getOrder().getTable().getTableNumber(),
            item.getMenuItem().getName(),
            item.getQuantity(),
            item.getExtras() != null ? item.getExtras() : "none",
            item.getNotes() != null ? item.getNotes() : "—"
        );
    }
}
```

### 📊 Diagramă UML
```
┌─────────────────────┐
│   <<interface>>     │
│   TicketPrinter     │
├─────────────────────┤
│ + printTicket()     │
└──────────┬──────────┘
           │
    ┌──────┴──────┬──────────────┐
    │             │              │
┌───▼───┐    ┌────▼────┐    ┌───▼──────┐
│Kitchen│    │   Bar   │    │  Pizza   │
└───────┘    └─────────┘    └──────────┘

TicketPrinterFactory
    ├─ create(KITCHEN) → KitchenTicketPrinter
    ├─ create(BAR) → BarTicketPrinter
    └─ create(PIZZA) → PizzaTicketPrinter
```

### ✅ Avantaje

| Avantaj | Explicație |
|---------|-----------|
| **Decuplare** | Clientul nu cunoaşte clasele concrete |
| **Extensibilitate** | Adaugă nou printer fără modificare existentă |
| **Centralizare** | Logică de creație într-un loc |
| **Mentenabilitate** | Uşor de modificat fără side-effects |

### 📌 Reguli de Utilizare

- Folosește când trebuie să creezi obiecte de tipuri diferite
- Client-ul nu trebuie să cunoască tipul exact
- Logica de selecție e în factory, nu în client

---

## Abstract Factory Pattern

### 🎯 Ce este?
**Abstract Factory** creează **familii de obiecte corelate** fără a specifica clasele concrete ale fiecăruia.

### 📍 Locație în Proiect
```
src/main/java/com/restaurant/pos/patterns/abstractfactory/
├── RestaurantModeFactory.java (abstract factory)
├── OrderFormConfig.java (abstract product 1)
├── ReceiptConfig.java (abstract product 2)
├── StandardRestaurantFactory.java (concrete factory)
└── BanquetRestaurantFactory.java (concrete factory)
```

### 🔧 Componentele

#### 1. **RestaurantModeFactory** (Abstract Factory)
```java
public interface RestaurantModeFactory {
    OrderFormConfig createOrderFormConfig();
    ReceiptConfig createReceiptConfig();
}
```

#### 2. **Abstract Products**

**OrderFormConfig:**
```java
public interface OrderFormConfig {
    boolean isDiscountAllowed();
    int getMaxItemsPerOrder();
    String getFormTitle();
}
```

**ReceiptConfig:**
```java
public interface ReceiptConfig {
    boolean showLogo();
    String getFooterMessage();
    boolean showItemizedTax();
}
```

#### 3. **Concrete Factories**

**StandardRestaurantFactory:**
```java
@Component("standardFactory")
public class StandardRestaurantFactory implements RestaurantModeFactory {
    @Override
    public OrderFormConfig createOrderFormConfig() {
        return new OrderFormConfig() {
            public boolean isDiscountAllowed() { return true; }
            public int getMaxItemsPerOrder()   { return 50; }
            public String getFormTitle()       { return "Standard Order"; }
        };
    }

    @Override
    public ReceiptConfig createReceiptConfig() {
        return new ReceiptConfig() {
            public boolean showLogo()          { return true; }
            public String getFooterMessage()   { return "Thank you for dining with us!"; }
            public boolean showItemizedTax()   { return false; }
        };
    }
}
```

**BanquetRestaurantFactory:**
```java
@Component("banquetFactory")
public class BanquetRestaurantFactory implements RestaurantModeFactory {
    @Override
    public OrderFormConfig createOrderFormConfig() {
        return new OrderFormConfig() {
            public boolean isDiscountAllowed() { return false; }
            public int getMaxItemsPerOrder()   { return 200; }
            public String getFormTitle()       { return "Banquet Order"; }
        };
    }

    @Override
    public ReceiptConfig createReceiptConfig() {
        return new ReceiptConfig() {
            public boolean showLogo()          { return true; }
            public String getFooterMessage()   { return "Thank you for choosing us for your event!"; }
            public boolean showItemizedTax()   { return true; }
        };
    }
}
```

### 📊 Comparație: Standard vs Banquet

```
┌─────────────────────────────────────────────┐
│ STANDARD MODE                               │
├─────────────────────────────────────────────┤
│ • Discount-uri: ✅ PERMISE                  │
│ • Max iteme: 50                             │
│ • Titlu: "Standard Order"                   │
│ • Tax detaliat: ❌ NU                       │
│ • Mesaj: "Thank you for dining with us!"   │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│ BANQUET MODE                                │
├─────────────────────────────────────────────┤
│ • Discount-uri: ❌ INTERZISE                │
│ • Max iteme: 200                            │
│ • Titlu: "Banquet Order"                    │
│ • Tax detaliat: ✅ DA                       │
│ • Mesaj: "Thank you for your event!"       │
└─────────────────────────────────────────────┘
```

### ✅ Avantaje

| Avantaj | Explicație |
|---------|-----------|
| **Coeziune** | Produsele din aceeași familie sunt corelate |
| **Consistență** | Configurări compatibile garantate |
| **Izolare** | Logici diferite complet separate |
| **Flexibilitate** | Ușor de adăuga noi moduri |

### 📌 Reguli de Utilizare

- Folosește când trebuie să creezi **grupe de obiecte corelate**
- Fiecare familie are propria fabrică
- Produsele din aceeași familie sunt consistente
- Extensia se face prin adăugare de noi fabrici, nu modificare existentă

---

## Facade Pattern

### 🎯 Ce este?
**Facade** oferă o interfață simplificată pentru un subsistem complex.

### 📍 Locație în Proiect
```
src/main/java/com/restaurant/pos/patterns/facade/
└── PlaceOrderFacade.java
```

### 🔧 Implementare

```java
@Component
public class PlaceOrderFacade {
    /**
     * Trimite comanda la toate stațiile corespunzătoare
     */
    public String sendOrder(Long orderId) {
        // Logică complexă pentru trimitere la multiple stații
        // - Validare ordine
        // - Creație bilete pentru fiecare tip de stație
        // - Trimitere la imprimante
        return null;
    }

    /**
     * Procesează plată și creează înregistrare
     */
    public Payment processPayment(Long orderId, PaymentMethod method, 
                                 BigDecimal discount, User waiter) {
        // Logică complexă pentru procesare plată
        // - Calcul total
        // - Aplicare discount
        // - Creație înregistrare plată
        // - Update status comandă
        return null;
    }
}
```

### 📊 Beneficii

- ✅ Simplifică operații complexe
- ✅ Decuplează client de subsistem
- ✅ Centralizează logică complexă
- ✅ Ușor de testat

---

## Comparații

### Factory Method vs Abstract Factory

```
┌─────────────────────────────────────────────────────────────┐
│ FACTORY METHOD PATTERN                                      │
├─────────────────────────────────────────────────────────────┤
│ • Creează: UN singur tip de obiect                          │
│ • Exemplu: TicketPrinterFactory → TicketPrinter            │
│ • Extensie: Adaugă noi metode în switch                    │
│ • Parametru: Enum (StationType)                             │
│ • Coupling: Slab (doar interfață)                           │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│ ABSTRACT FACTORY PATTERN                                    │
├─────────────────────────────────────────────────────────────┤
│ • Creează: FAMILII de obiecte corelate                      │
│ • Exemplu: RestaurantModeFactory → FormConfig + ReceiptConfig
│ • Extensie: Adaugă nouă fabrică concretă                   │
│ • Parametru: Factory selection (@Qualifier)                │
│ • Coupling: Puternic (produsele sunt corelate)              │
└─────────────────────────────────────────────────────────────┘
```

---

## FAQ

### **Q1: Cât se folosește Factory Method?**
**R:** Oricând trebuie să creezi obiecte de tipuri diferite bazat pe o condiție (enum, string, etc.) și vrei să decuplezi client-ul de clase concrete.

**Exemplu:** Tipul de ticket printer se selectează pe baza StationType.

---

### **Q2: Cât se folosește Abstract Factory?**
**R:** Când trebuie să creezi **grupe de obiecte care funcționează împreună** și sunt consistente ca o familie.

**Exemplu:** Modurile restaurant (Standard/Banquet) au propria configurare de formular + chitanță care sunt complementare.

---

### **Q3: Pot combina Factory Method cu Abstract Factory?**
**R:** Da! Abstract Factory poate folosi Factory Method pentru crearea produselor.

---

### **Q4: Ce se întâmplă dacă adaug o nouă stație (de ex. GRILL)?**
**R:** Adaugă:
1. `GrillTicketPrinter implements TicketPrinter`
2. Caz nou în `TicketPrinterFactory.create()`

Nu se modifică niciunul din codul existenț!

---

### **Q5: Cum testez pattern-urile?**
**R:** Mock-ează interfețele:
```java
@Test
public void testKitchenTicketFormat() {
    TicketPrinter printer = new KitchenTicketPrinter();
    String ticket = printer.printTicket(mockItem);
    assertTrue(ticket.contains("[KITCHEN TICKET]"));
}
```

---

### **Q6: De ce sunt important aceste pattern-uri?**
**R:**
- 🔄 **Reutilizabilitate** - Cod care se poate refolosi
- 🛡️ **Mentenabilitate** - Uşor de modificat
- 📈 **Scalabilitate** - Ușor de extins
- 🧪 **Testabilitate** - Ușor de testat izolat
- 👥 **Comunicare** - Codul "vorbește singur"

---

## 🎯 Concluzie

Cele trei pattern-uri în Restaurant POS:

| Pattern | Scop | Exemplu |
|---------|------|---------|
| **Factory Method** | Creează UN obiect corect | TicketPrinterFactory |
| **Abstract Factory** | Creează grupe corelate | RestaurantModeFactory |
| **Facade** | Simplifica complex | PlaceOrderFacade |

Folosite corect, transformă codul din "spaghetti" în **arhitectură elegantă și scalabilă**! 🚀

