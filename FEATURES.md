# SecondHand Marketplace - Complete Feature Guide

## 🎨 New UI Improvements

The web interface has been completely redesigned with:
- **Modern gradient styling** with professional color scheme (purple/blue gradients)
- **Responsive card-based layouts** with smooth hover effects
- **Status badges** with color-coding (Active, Pending, Sold, Completed)
- **Statistics dashboard** with key metrics
- **Mobile-friendly responsive design**
- **Professional typography and spacing**

---

## ✨ New Features Implemented

### 1. **Payment Management**
- Process payments for orders
- Track payment status (Pending → Completed or Refunded)
- Store payment method information
- Display payment details on order pages

**Database:** `Payments` table stores order payments with status tracking

---

### 2. **Delivery Tracking**
- Automatic tracking ID generation for each delivery
- Delivery status updates (Pending → In Transit → Delivered)
- Delivery partner assignment
- Track pickup and delivery dates
- Full integration with orders

**Database:** `Deliveries` table tracks all shipment details

---

### 3. **Messaging System**
- Send messages between buyers and sellers
- Inbox view with message list
- Mark messages as read/unread
- User-to-user communication

**Database:** `Messages` table stores all communications

---

### 4. **Buyer Dashboard** (`/buyer/dashboard`)
- View total orders and completed purchases
- Browse order history with status and tracking
- See delivery tracking information
- Quick access to product browsing
- Statistics overview

**Who can access:** Buyers only

---

### 5. **Seller Dashboard** (`/seller/dashboard`)
- View all your product listings
- Track available and sold items
- See statistics: total listings, available count, orders received
- Quick add new listing button
- Product grid view with full details

**Who can access:** Sellers only

---

### 6. **Admin Dashboard** (`/admin/dashboard`)
- View total users on platform
- Track all orders system-wide
- User management overview
- Monitor platform activity
- Access administrative controls

**Who can access:** Admins only (requires Admin registration)

---

### 7. **Messages Center** (`/messages`)
- View inbox messages from other users
- See unread message status
- Access contact information
- User-to-user communication hub

**Who can access:** All authenticated users

---

## 🏗️ Architecture Improvements

### GRASP Principles Compliance:
✅ **Information Expert** - DAOs handle database operations
✅ **Creator** - Controllers create domain objects
✅ **Controller** - Web handlers manage HTTP requests
✅ **Low Coupling** - Clean separation between layers
✅ **High Cohesion** - Each class has focused responsibility
✅ **Polymorphism** - User subclasses (Buyer, Seller, Admin)
✅ **Indirection** - Web handlers route through controllers

### Database Structure:
```
Users (user roles: Buyer, Seller, Admin)
  ↓
Products (seller_id)
  ↓
Orders (buyer_id, product_id)
  ├→ Payments
  ├→ Deliveries
  └→ Messages
```

---

## 📊 Key Pages & Routes

| Route | Description | Auth |
|-------|-------------|------|
| `/` | Homepage with product listings | Optional |
| `/login` | User login | No |
| `/register` | New account registration | No |
| `/products` | Browse all products | Optional |
| `/product/add` | Add new product listing | Seller |
| `/orders` | View buyer orders | Buyer |
| `/buyer/dashboard` | Buyer dashboard | Buyer |
| `/seller/dashboard` | Seller dashboard | Seller |
| `/admin/dashboard` | Admin dashboard | Admin |
| `/messages` | Message inbox | Any |
| `/logout` | Sign out | Any |

---

## 🎯 Role-Based Features

### 👤 Buyer
- Browse products
- Place orders
- Track deliveries
- View order history
- Send messages to sellers
- View payment status

### 🏪 Seller
- Create product listings
- Manage inventory
- View received orders
- Track sales statistics
- Send messages to buyers
- Update product details

### 🔐 Admin
- View all users
- Monitor all orders
- Access platform statistics
- Manage user accounts
- System oversight

---

## 🗄️ Updated Database Schema

**New Tables:**
```sql
Payments - order payments tracking
Deliveries - shipment tracking and delivery info
Messages - user-to-user messaging
```

Run `database-schema.sql` to set up all tables.

---

## 🚀 Running the Application

1. **Initialize database:**
   ```sql
   source database-schema.sql
   ```

2. **Compile:**
   ```bash
   cd src
   javac -cp ../lib/mysql-connector-j-9.6.0/* model/*.java controller/*.java dao/*.java view/*.java *.java
   ```

3. **Run:**
   ```bash
   java -cp ".;../lib/mysql-connector-j-9.6.0/*" Main
   ```

4. **Access:** http://localhost:8080

---

## 🎨 UI/UX Highlights

- **Color Scheme:** Purple/Blue gradients with white cards
- **Typography:** Modern Segoe UI font
- **Spacing:** 30px container padding, consistent gaps
- **Shadows:** Subtle elevation effects for depth
- **Hover Effects:** Cards lift on hover with enhanced shadow
- **Status Colors:** 
  - Green for Active/Completed
  - Yellow for Pending
  - Red for Sold
  - Blue for Info

---

## ✅ Features Following GRASP

1. **Creator**: WebServer creates handler instances
2. **Information Expert**: DAOs manage all data persistence
3. **Controller**: Controllers delegate to DAOs
4. **Low Coupling**: Clear dependency injection
5. **Polymorphism**: User subclasses override behavior
6. **Pure Fabrication**: DAO layer as data service
7. **Indirection**: HTML Templates decouple views from logic

---

## 🔐 Security Notes

- Session-based authentication with HTTP-only cookies
- Password stored in database (consider hashing in production)
- Role-based access control on all protected routes
- Database connection pooling for efficiency

---

## 📈 Next Steps to Enhance

- Add payment gateway integration (Stripe, PayPal)
- Implement email notifications
- Add product image uploads
- Implement real-time notifications
- Add review/rating system
- Enhance search and filtering
- Add wishlist feature
- Implement transaction history

---

Enjoy the enhanced SecondHand Marketplace platform! 🎉