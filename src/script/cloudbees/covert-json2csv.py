import json
import csv
import os
import datetime

def convert_timestamp_to_date(timestamp):
    """Convert Unix timestamp to human-readable date."""
    dt = datetime.datetime.fromtimestamp(timestamp)
    return dt.strftime('%Y-%m-%d')

def convert_date_to_timestamp(date_str):
    """Convert date string to Unix timestamp."""
    dt = datetime.datetime.strptime(date_str, '%Y-%m')
    return dt.timestamp()

def convert_json_to_csv(input_file, output_file, start_date, end_date):
    """Read JSON file known format and convert to CSV file."""
    try:
        # Convert date strings to Unix timestamps
        start_timestamp = convert_date_to_timestamp(start_date)
        end_timestamp = convert_date_to_timestamp(end_date)

        # Read the JSON file
        with open(input_file, 'r') as json_file:
            data = json.load(json_file)

        # Open a CSV file for writing
        with open(output_file, 'w', newline='') as csv_file:
            csv_writer = csv.writer(csv_file)
            
            # Write the header
            csv_writer.writerow(['Last active', 'User', 'Email', 'Access Type'])
            
            # Initialize counters and sets for tracking IDs
            total_entries = 0
            unique_ids = set()
            duplicate_ids = set()
            
            # Process each entry in the JSON data
            for entry, timestamp in data.items():
                #correct this filter to be inclusive with start and end dates
                if start_timestamp <= timestamp <= end_timestamp:
                    parts = entry.split(',')
                    if len(parts) == 3:
                        row_data = parts
                    elif len(parts) == 2:
                        row_data = parts + ['']
                    else:
                        continue
                    
                    # Convert the timestamp to a human-readable date
                    human_readable_date = convert_timestamp_to_date(timestamp)
                    
                    # Reorder the row data to match the new header positions
                    row_data = [human_readable_date, row_data[0], row_data[2], row_data[1]]
                    
                    csv_writer.writerow(row_data)
                    total_entries += 1
                    if parts[0] in unique_ids:
                        duplicate_ids.add(parts[0])
                    else:
                        unique_ids.add(parts[0])
            
            print(f"""Summary:
Filter from {start_date} to {end_date}
Total entries: {total_entries}
Unique IDs: {len(unique_ids)}
Duplicate IDs: {len(duplicate_ids)}
""")
    except Exception as e:
        print(f"An error occurred: {e}")

# Filter: 12 months data
start_date = '2024-01'
end_date = '2025-01'
# Define the relative path to the mock user activity JSON file
script_dir = os.path.dirname(__file__)
#input_file = os.path.join(script_dir, '../../resources/cloudbees/mock-user-activity-2.json')
input_file =  '/tmp/input.json'
output_file = os.path.join(script_dir, f'output_{start_date}_to_{end_date}.csv')


convert_json_to_csv(input_file, output_file, start_date, end_date)
