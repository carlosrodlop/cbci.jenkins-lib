import csv
import os
from datetime import datetime

def combine_csv_files(input_file1, input_file2, output_file):
    """Combine two CSV files into one. When it finds a user with the same name, it keeps the most recent record."""
    try:
        user_records = {}
        total_entries = 0

        # Function to read data from a CSV file and update user_records
        def read_csv_and_update_records(input_file):
            nonlocal total_entries
            with open(input_file, 'r', encoding='ISO-8859-1') as csv_input_file:
                csv_reader = csv.reader(csv_input_file)
                next(csv_reader)  # Skip the header
                for row in csv_reader:
                    total_entries += 1
                    user = row[1]
                    last_active = datetime.strptime(row[0], '%Y-%m-%d')
                    if user not in user_records or last_active > user_records[user][0]:
                        user_records[user] = (last_active, row)

        # Read data from the first CSV file
        read_csv_and_update_records(input_file1)
        
        # Read data from the second CSV file
        read_csv_and_update_records(input_file2)

        # Open the output CSV file for writing
        with open(output_file, 'w', newline='', encoding='ISO-8859-1') as csv_output_file:
            csv_writer = csv.writer(csv_output_file)
            
            # Write the header
            csv_writer.writerow(['Last active', 'User', 'Email', 'Access Type'])
            
            # Write the most recent records for each user
            for last_active, row in user_records.values():
                csv_writer.writerow(row)
        
        print(f"Combined CSV file created: {output_file}")
        
        # Print summary
        unique_records = len(user_records)
        print(f"""Summary:
Total number of entries: {total_entries}
Unique records: {unique_records}
""")
    except Exception as e:
        print(f"An error occurred: {e}")

# Define the relative paths to the input CSV files
script_dir = os.path.dirname(__file__)
input_file1 = os.path.join(script_dir, '../../resources/cloudbees/mock-user-activity-1.csv')
#input_file1 = '/tmp/input1.csv'
input_file2 = os.path.join(script_dir, '../../resources/cloudbees/mock-user-activity-2.csv')
#input_file2 = '/tmp/input2.csv'
output_file = os.path.join(script_dir, 'combined_output_hsbc.csv')

combine_csv_files(input_file1, input_file2, output_file)
