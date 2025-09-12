-- Insert sample FoodItem data (all restaurant_id = 1)
INSERT INTO food_item
(name, description, quantity, unit, condition, food_category, food_item_status,
 halal_certified, kosher_certified, vegetarian_friendly, created_at, expiry_date, restaurant_id)
VALUES
-- Fruits
('Fresh Apples', 'Crisp and sweet red apples freshly harvested from the farm.', 50, 'KG', 'FRESH', 'FRUITS', 'AVAILABLE',
 true, true, true, NOW(), '2025-09-30 18:00:00', 1),

('Bananas', 'Ripe yellow bananas ideal for snacks and smoothies.', 60, 'KG', 'FRESH', 'FRUITS', 'AVAILABLE',
 true, true, true, NOW(), '2025-09-16 09:00:00', 1),

-- Meat & Poultry
('Chicken Breast', 'Skinless, boneless chicken breast fillets vacuum-sealed for freshness.', 25, 'KG', 'FROZEN', 'POULTRY', 'AVAILABLE',
 true, false, false, NOW(), '2025-09-20 12:00:00', 1),

('Beef Mince', 'Fresh ground beef suitable for burgers and meatballs.', 20, 'KG', 'FRESH', 'MEAT', 'AVAILABLE',
 true, false, false, NOW(), '2025-09-22 14:00:00', 1),

-- Vegetables
('Fresh Carrots', 'Crunchy organic carrots suitable for soups and salads.', 30, 'KG', 'FRESH', 'VEGETABLES', 'AVAILABLE',
 true, true, true, NOW(), '2025-09-25 15:30:00', 1),

('Potatoes', 'Washed white potatoes suitable for frying and mashing.', 100, 'KG', 'FRESH', 'VEGETABLES', 'AVAILABLE',
 true, true, true, NOW(), '2025-09-29 17:00:00', 1),

-- Dairy
('Mozzarella Cheese', 'Soft Italian mozzarella cheese blocks perfect for pizzas.', 15, 'KG', 'PERISHABLE', 'DAIRY', 'AVAILABLE',
 true, false, true, NOW(), '2025-09-18 11:00:00', 1),

-- Bakery
('Whole Wheat Bread', 'Freshly baked whole wheat bread with high fiber content.', 40, 'UNITS', 'FRESH', 'BAKERY', 'AVAILABLE',
 true, true, true, NOW(), '2025-09-15 08:00:00', 1),

-- Seafood
('Salmon Fillet', 'Fresh Norwegian salmon fillets perfect for grilling or sushi.', 10, 'KG', 'FRESH', 'FISH', 'AVAILABLE',
 false, true, false, NOW(), '2025-09-19 17:00:00', 1),

-- Grains
('Cooked Rice', 'Steamed white rice stored hygienically and ready to serve.', 100, 'PORTIONS', 'COOKED', 'GRAINS', 'AVAILABLE',
 true, true, true, NOW(), '2025-09-12 20:00:00', 1);
