import json
import csv
import os
import datetime

def convert_timestamp_to_date(timestamp):
    """Convert Unix timestamp to human-readable date."""
    dt = datetime.datetime.fromtimestamp(timestamp)
    return dt.strftime('%Y-%m-%d %H:%M:%S.%f')

def convert_json_to_csv(input_file, output_file):
    try:
        # Read the JSON file
        with open(input_file, 'r') as json_file:
            data = json.load(json_file)

        # Open a CSV file for writing
        with open(output_file, 'w', newline='') as csv_file:
            csv_writer = csv.writer(csv_file)
            
            # Write the header
            csv_writer.writerow(['ID', 'Action', 'Email', 'Timestamp', 'Human-readable Date'])
            
            # Initialize counters and sets for tracking IDs
            total_entries = 0
            unique_ids = set()
            duplicate_ids = set()
            
            # Process each entry in the JSON data
            for entry, timestamp in data.items():
                parts = entry.split(',')
                if len(parts) == 3:
                    row_data = parts
                elif len(parts) == 2:
                    row_data = parts + ['']
                else:
                    continue
                
                # Convert the timestamp to a human-readable date
                human_readable_date = convert_timestamp_to_date(timestamp)
                
                # Add the timestamp and human-readable date to the row data
                row_data.extend([timestamp, human_readable_date])
                
                csv_writer.writerow(row_data)
                total_entries += 1
                if parts[0] in unique_ids:
                    duplicate_ids.add(parts[0])
                else:
                    unique_ids.add(parts[0])
            
            print(f"Total entries: {total_entries}")
            print(f"Unique IDs: {len(unique_ids)}")
            print(f"Duplicate IDs: {len(duplicate_ids)}")
    except Exception as e:
        print(f"An error occurred: {e}")

# Define the relative path to the mock user activity JSON file
script_dir = os.path.dirname(__file__)
input_file = os.path.join(script_dir, '../../resources/cloudbees/mock-user-activity.json')
output_file = os.path.join(script_dir, 'output-v2.csv')

# Convert JSON to CSV
convert_json_to_csv(input_file, output_file)
