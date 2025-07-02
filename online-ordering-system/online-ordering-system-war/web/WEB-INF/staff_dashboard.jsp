<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page session="true" %>
<%
    // Check if user is logged in and is managing staff
    if (session.getAttribute("staffId") == null || 
        !"managing".equals(session.getAttribute("staffRole"))) {
        response.sendRedirect("login.jsp");
        return;
    }
    String staffName = (String) session.getAttribute("staffName");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Managing Staff Dashboard - APU Convenience Store</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f6fa;
        }
        
        .navbar {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .navbar h1 {
            font-size: 24px;
        }
        
        .navbar .user-info {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        
        .logout-btn {
            background: rgba(255,255,255,0.2);
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            transition: background 0.3s;
        }
        
        .logout-btn:hover {
            background: rgba(255,255,255,0.3);
        }
        
        .container {
            max-width: 1200px;
            margin: 30px auto;
            padding: 0 20px;
        }
        
        .welcome-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            margin-bottom: 30px;
            text-align: center;
        }
        
        .welcome-section h2 {
            color: #333;
            margin-bottom: 10px;
        }
        
        .welcome-section p {
            color: #666;
            font-size: 16px;
        }
        
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .dashboard-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            transition: transform 0.3s;
        }
        
        .dashboard-card:hover {
            transform: translateY(-5px);
        }
        
        .dashboard-card h3 {
            color: #333;
            margin-bottom: 15px;
            font-size: 20px;
        }
        
        .dashboard-card p {
            color: #666;
            margin-bottom: 20px;
            line-height: 1.6;
        }
        
        .card-actions {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }
        
        .btn {
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-decoration: none;
            font-size: 14px;
            transition: all 0.3s;
            display: inline-block;
            text-align: center;
        }
        
        .btn-primary {
            background: #667eea;
            color: white;
        }
        
        .btn-primary:hover {
            background: #5a6fd8;
        }
        
        .btn-success {
            background: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background: #218838;
        }
        
        .btn-info {
            background: #17a2b8;
            color: white;
        }
        
        .btn-info:hover {
            background: #138496;
        }
        
        .btn-warning {
            background: #ffc107;
            color: #212529;
        }
        
        .btn-warning:hover {
            background: #e0a800;
        }
        
        .stats-section {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            text-align: center;
        }
        
        .stat-number {
            font-size: 36px;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 10px;
        }
        
        .stat-label {
            color: #666;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <h1>APU Convenience Store - Management Dashboard</h1>
        <div class="user-info">
            <span>Welcome, <%= staffName %> (Managing Staff)</span>
            <a href="logout" class="logout-btn">Logout</a>
        </div>
    </nav>
    
    <div class="container">
        <div class="welcome-section">
            <h2>Management Dashboard</h2>
            <p>Manage your store operations, staff, customers, and products efficiently</p>
        </div>
        
        <!-- Quick Stats -->
        <div class="stats-section">
            <div class="stat-card">
                <div class="stat-number">25</div>
                <div class="stat-label">Total Orders Today</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">8</div>
                <div class="stat-label">Active Staff</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">150</div>
                <div class="stat-label">Registered Customers</div>
            </div>
            <div class="stat-card">
                <div class="stat-number">85</div>
                <div class="stat-label">Products Available</div>
            </div>
        </div>
        
        <!-- Management Functions -->
        <div class="dashboard-grid">
            <!-- Staff Management -->
            <div class="dashboard-card">
                <h3>Staff Management</h3>
                <p>Manage all staff information, roles, and availability. Add new staff members or update existing records.</p>
                <div class="card-actions">
                    <a href="addStaff.jsp" class="btn btn-success">Add Staff</a>
                    <a href="viewStaff.jsp" class="btn btn-info">View All Staff</a>
                    <a href="searchStaff.jsp" class="btn btn-primary">Search Staff</a>
                </div>
            </div>
            
            <!-- Customer Management -->
            <div class="dashboard-card">
                <h3>Customer Management</h3>
                <p>View and manage customer accounts, search customer information, and handle customer-related operations.</p>
                <div class="card-actions">
                    <a href="viewCustomers.jsp" class="btn btn-info">View Customers</a>
                    <a href="searchCustomers.jsp" class="btn btn-primary">Search Customers</a>
                </div>
            </div>
            
            <!-- Product Management -->
            <div class="dashboard-card">
                <h3>Product Management</h3>
                <p>Manage store inventory, add new products, update prices and stock levels, and organize product categories.</p>
                <div class="card-actions">
                    <a href="addProduct.jsp" class="btn btn-success">Add Product</a>
                    <a href="viewProducts.jsp" class="btn btn-info">View Products</a>
                    <a href="updateStock.jsp" class="btn btn-warning">Update Stock</a>
                </div>
            </div>
            
            <!-- Order Management -->
            <div class="dashboard-card">
                <h3>Order Management</h3>
                <p>View all orders, assign delivery staff to orders, and track order status and delivery progress.</p>
                <div class="card-actions">
                    <a href="viewOrders.jsp" class="btn btn-info">View All Orders</a>
                    <a href="assignDelivery.jsp" class="btn btn-primary">Assign Delivery</a>
                    <a href="orderTracking.jsp" class="btn btn-warning">Track Orders</a>
                </div>
            </div>
            
            <!-- Feedback & Ratings -->
            <div class="dashboard-card">
                <h3>Feedback & Ratings</h3>
                <p>View customer feedback, ratings, and reviews to improve service quality and customer satisfaction.</p>
                <div class="card-actions">
                    <a href="viewFeedback.jsp" class="btn btn-info">View Feedback</a>
                    <a href="ratingAnalysis.jsp" class="btn btn-primary">Rating Analysis</a>
                </div>
            </div>
            
            <!-- Reports -->
            <div class="dashboard-card">
                <h3>Reports & Analytics</h3>
                <p>Generate various reports for sales, staff performance, customer analytics, and business insights.</p>
                <div class="card-actions">
                    <a href="salesReport.jsp" class="btn btn-info">Sales Report</a>
                    <a href="staffReport.jsp" class="btn btn-primary">Staff Report</a>
                    <a href="customerReport.jsp" class="btn btn-warning">Customer Report</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>