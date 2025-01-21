import json
import csv
import os

def convert_json_to_csv(input_file, output_file):
    # Read the JSON file
    with open(input_file, 'r') as json_file:
        data = json.load(json_file)

    # Open a CSV file for writing
    with open(output_file, 'w', newline='') as csv_file:
        csv_writer = csv.writer(csv_file)
        
        # Write the header
        csv_writer.writerow(['ID', 'Action', 'Email'])
        
        # Initialize counters and sets for tracking IDs
        total_entries = 0
        unique_ids = set()
        duplicate_ids = set()
        
        # Process each entry in the JSON data
        for entry in data:
            parts = entry.split(',')
            if len(parts) == 3:
                row_data = parts
            elif len(parts) == 2:
                row_data = parts + ['']
            else:
                row_data = [parts[0], parts[1], '']
            
            # Write the row to the CSV file
            csv_writer.writerow(row_data)
            
            # Update counters and sets
            total_entries += 1
            id_value = parts[0]
            if id_value in unique_ids:
                duplicate_ids.add(id_value)
            else:
                unique_ids.add(id_value)
    
        
        # Print the results
        print(f"Total entries processed: {total_entries}")
        print(f"Total unique IDs: {len(unique_ids)}")
        print(f"Duplicate IDs: {len(duplicate_ids)}")

# Define the relative path to the mock user activity JSON file
script_dir = os.path.dirname(__file__)
input_file = os.path.join(script_dir, '../../../resources/cloudbees/mock-user-activity.json')
output_file = os.path.join(script_dir, 'output.csv')

# Convert JSON to CSV
convert_json_to_csv(input_file, output_file)