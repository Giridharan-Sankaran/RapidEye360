from flask import Flask, jsonify, request
from pymongo import MongoClient

app = Flask(__name__)

client = MongoClient('mongodb+srv://rapideye6:qwertyuiop@rapideye360.hwak4ul.mongodb.net/')
db = client['rapideye360']

response_struct = {}


@app.route('/subcategory/product', methods=['GET'])
def get_subcategory_product():

    try:

        collection = db['CumulativeData']
        data = request.json
        month = data.get('month')
        year = data.get('year')
        subcategory = data.get('subcategory')
        category = data.get('category')

        data = collection.find()
        graph_data = []
        for item in data:
            if item['month'] == month and item['subcategory'] == subcategory and item['category'] == category and item['year'] == year:
                graph_data.append({
                    'quantity': item['quantitysold'],
                    'month': item['month']
                }) 
        
        response_struct = {'data': graph_data, 'err':{}}
        return jsonify(response_struct)

    except Exception as err:
        response_struct = {'data': {}, 'err':err}
        return jsonify(response_struct)
        
    

@app.route('/category/brand/piechart', methods=['GET'])
def get_category_brand_percentage():
        
    try:    
        collection = db['CumulativeData']
        data = request.json
        month = data.get('month')
        year = data.get('year')
        category = data.get('category')
        
        data = collection.find()

        brand_quantity = {}
        total_quantity = 0
        for item in data:
            if item['month'] == month and item['category'] == category and item['year'] == year:
                brand = item['brand']
                quantity = item['quantitysold']
                total_quantity += quantity
                brand_quantity[brand] = brand_quantity.get(brand, 0) + quantity

        graph_data = []
        for brand, quantity in brand_quantity.items():
            percentage = (quantity / total_quantity) * 100 if total_quantity > 0 else 0
            graph_data.append({'brand': brand, 'percentage': round(percentage, 2)})  
        
        response_struct = {'data': graph_data, 'err':{}}
        return jsonify(response_struct)
    
    except Exception as err:
        response_struct = {'data': {}, 'err':err}
        return jsonify(response_struct)



@app.route('/category/product/piechart', methods=['GET'])
def get_category_product_percentage():

    try:
        collection = db['CumulativeData']
        data = request.json
        month = data.get('month')
        year = data.get('year')
        category = data.get('category')
        
        data = collection.find()

        category_quantity = {}
        total_quantity = 0
        for item in data:
            if item['month'] == month and item['category'] == category and item['year'] == year:
                quantity = item['quantitysold']
                total_quantity += quantity
                category_quantity[category] = category_quantity.get(category, 0) + quantity

        graph_data = []
        for category, quantity in category_quantity.items():
            percentage = (quantity / total_quantity) * 100 if total_quantity > 0 else 0
            graph_data.append({'category': category, 'percentage': round(percentage, 2)})  
        
        response_struct = {'data': graph_data, 'err':{}}
        return jsonify(response_struct)
    
    except Exception as err:
        response_struct = {'data': {}, 'err':err}
        return jsonify(response_struct)



@app.route('/subcategory/product/timerange', methods=['GET'])
def get_subcategory_product_timerange():

    try:
        collection = db['CumulativeData']
        data = request.json
        startmonth = data.get('startMonth')
        endmonth = data.get('endMonth')
        year = data.get('year')
        subcategory = data.get('subcategory')
        category = data.get('category')

        data = list(collection.find())
        
        graph_data = []
        for item in data:
            if item['month'] >= startmonth and item['month'] <= endmonth and item['year'] == year and item['subcategory'] == subcategory and item['category'] == category:
                graph_data.append({
                    'quantity': item['quantitysold'],
                    'month': item['month']
                })
        
        response_struct = {'data': graph_data, 'err':{}}
        return jsonify(response_struct)
    
    except Exception as err:
        response_struct = {'data': {}, 'err':err}
        return jsonify(response_struct)


@app.route('/category/product/timerange', methods=['GET'])
def get_category_product_timerange():

    try:
        collection = db['CumulativeData']
        data = request.json
        startmonth = data.get('startMonth')
        endmonth = data.get('endMonth')
        year = data.get('year')
        category = data.get('category')

        data = list(collection.find())
        
        graph_data = []
        for item in data:
            if item['month'] >= startmonth and item['month'] <= endmonth and item['year'] == year and item['category'] == category:
                graph_data.append({
                    'quantity': item['quantitysold'],
                    'month': item['month']
                })
        
        response_struct = {'data': graph_data, 'err':{}}
        return jsonify(response_struct)

    except Exception as err:
        response_struct = {'data': {}, 'err':err}
        return jsonify(response_struct)


@app.route('/category/brand/timerange', methods=['GET'])
def get_category_brand_timerange():

    try:
        collection = db['CumulativeData']
        data = request.json
        startmonth = data.get('startMonth')
        endmonth = data.get('endMonth')
        year = data.get('year')
        category = data.get('category')
        brand = data.get('brand')

        data = list(collection.find())
        
        graph_data = []
        for item in data:
            if item['month'] >= startmonth and item['month'] <= endmonth and item['year'] == year and item['category'] == category and item['brand'] == brand:
                graph_data.append({
                    'quantity': item['quantitysold'],
                    'month': item['month']
                })
        
        response_struct = {'data': graph_data, 'err':{}}
        return jsonify(response_struct)
    
    except Exception as err:
        response_struct = {'data': {}, 'err':err}
        return jsonify(response_struct)

if __name__ == '__main__':
    app.run(debug=True)
