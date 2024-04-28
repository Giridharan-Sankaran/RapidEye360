import unittest
from flask_testing import TestCase
from re360graphs import app, db  

class MyTest(TestCase):

    def create_app(self):
        app.config['TESTING'] = True
        return app

    def setUp(self):
        self.client = app.test_client()


class TestRoutes(MyTest):

    def test_get_subcategory_product(self):
        response = self.client.get('/subcategory/product', json={'month': 'January', 'year': '2024', 'subcategory': 'Electronics', 'category': 'Gadgets'})
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json['data'], list)

    def test_get_category_brand_percentage(self):
        response = self.client.get('/category/brand/piechart', json={'month': 'January', 'year': '2024', 'category': 'Gadgets'})
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json['data'], list)

    def test_get_category_product_percentage(self):
        response = self.client.get('/category/product/piechart', json={'month': 'January', 'year': '2024', 'category': 'Gadgets'})
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json['data'], list)

    def test_get_subcategory_product_timerange(self):
        response = self.client.get('/subcategory/product/timerange', json={'startMonth': 'January', 'endMonth': 'March', 'year': '2024', 'subcategory': 'Electronics', 'category': 'Gadgets'})
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json['data'], list)

    def test_get_category_product_timerange(self):
        response = self.client.get('/category/product/timerange', json={'startMonth': 'January', 'endMonth': 'March', 'year': '2024', 'category': 'Gadgets'})
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json['data'], list)

    def test_get_category_brand_timerange(self):
        response = self.client.get('/category/brand/timerange', json={'startMonth': 'January', 'endMonth': 'March', 'year': '2024', 'category': 'Gadgets', 'brand': 'Samsung'})
        self.assertEqual(response.status_code, 200)
        self.assertIsInstance(response.json['data'], list)

if __name__ == '__main__':
    unittest.main()
