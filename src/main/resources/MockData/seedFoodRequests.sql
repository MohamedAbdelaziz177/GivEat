-- Insert sample FoodRequest data
INSERT INTO "food-requests"
(name, quantity, unit, expiry_limit, condition, status, food_category,
 urgency, requires_halal, requires_kosher, vegetarian_only,
 request_date, charity_id)
VALUES
-- Fruits
('Apples for Kids', 20, 'KG', '2025-09-29 12:00:00', 'FRESH', 'PENDING', 'FRUITS',
 3, true, true, true, NOW(), 1),

('Banana Snacks', 15, 'KG', '2025-09-15 10:00:00', 'FRESH', 'PENDING', 'FRUITS',
 2, true, true, true, NOW(), 2),

-- Meat & Poultry
('Chicken for Soup Kitchen', 10, 'KG', '2025-09-19 18:00:00', 'FROZEN', 'PENDING', 'POULTRY',
 5, true, false, false, NOW(), 1),

('Beef Mince for Burgers', 8, 'KG', '2025-09-21 14:00:00', 'FRESH', 'PENDING', 'MEAT',
 4, true, false, false, NOW(), 2),

-- Vegetables
('Carrots for Stew', 10, 'KG', '2025-09-24 12:00:00', 'FRESH', 'PENDING', 'VEGETABLES',
 3, true, true, true, NOW(), 1),

('Potatoes for Families', 40, 'KG', '2025-09-28 16:00:00', 'FRESH', 'PENDING', 'VEGETABLES',
 4, true, true, true, NOW(), 2),

-- Dairy
('Mozzarella for Pizza', 5, 'KG', '2025-09-17 09:00:00', 'PERISHABLE', 'PENDING', 'DAIRY',
 2, true, false, true, NOW(), 1),

-- Bakery
('Bread for Daily Meals', 20, 'UNITS', '2025-09-14 07:00:00', 'FRESH', 'PENDING', 'BAKERY',
 5, true, true, true, NOW(), 2),

-- Seafood
('Salmon for Orphanage', 5, 'KG', '2025-09-18 20:00:00', 'FRESH', 'PENDING', 'FISH',
 1, false, true, false, NOW(), 1),

-- Grains
('Cooked Rice Portions', 50, 'PORTIONS', '2025-09-12 19:00:00', 'COOKED', 'PENDING', 'GRAINS',
 5, true, true, true, NOW(), 2);
