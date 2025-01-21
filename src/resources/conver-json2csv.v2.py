import json
import csv
from datetime import datetime

# ...existing code...

def convert_timestamp_to_human_readable(timestamp):
    return datetime.fromtimestamp(timestamp).strftime('%Y-%m-%d %H:%M:%S')

def json_to_csv(json_file, csv_file):
    with open(json_file, 'r') as f:
        data = json.load(f)
    
    with open(csv_file, 'w', newline='') as csvfile:
        fieldnames = ['Key', 'Timestamp', 'HumanReadableTime']
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        
        writer.writeheader()
        for key, value in data.items():
            if isinstance(value, (int, float)):
                human_readable_time = convert_timestamp_to_human_readable(value)
                writer.writerow({'Key': key, 'Timestamp': value, 'HumanReadableTime': human_readable_time})
            else:
                writer.writerow({'Key': key, 'Timestamp': '', 'HumanReadableTime': ''})

# ...existing code...
