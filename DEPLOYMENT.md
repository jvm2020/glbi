# GLBI Simulator - Deployment Guide

## Overview
This is a standalone web application for calculating Guaranteed Livable Basic Income (GLBI) benefits across Canada, based on the Parliamentary Budget Officer (PBO) report from February 2025.

## Deployment URL
**simulator.glbi.ca**

## Files Structure
```
/
├── index.html          # Main simulator page with calculator
├── references.html     # Comprehensive references and resources
├── styles.css          # Styling for both pages
├── calculator.js       # Calculator logic and UI updates
├── data.js            # Federal rates and provincial data
└── README.md          # Repository information
```

## Deployment Requirements
- **Hosting**: Any static web host (GitHub Pages, Netlify, Vercel, Apache, nginx)
- **Dependencies**: None - pure HTML/CSS/JavaScript
- **Server**: No server-side processing required
- **Database**: No database required

## Quick Deploy
1. Upload all files to web server root or subdomain directory
2. Ensure index.html is set as the default document
3. Configure domain/subdomain to point to the directory
4. No build process required - files are ready to serve

## Browser Support
- Chrome/Edge (latest)
- Firefox (latest)
- Safari (latest)
- Mobile browsers

## Features
- ✅ Interactive GLBI calculator
- ✅ Three reduction rate scenarios (50%, 25%, 15%)
- ✅ Disability amount calculations
- ✅ Provincial/territorial comparisons
- ✅ Comprehensive references with links to all source materials
- ✅ Responsive design for all devices
- ✅ No cookies or tracking
- ✅ Client-side calculations only

## Configuration
No configuration needed. All data is embedded in data.js and can be updated by modifying the GLBI_DATA object.

## Updates
To update federal rates or provincial data:
1. Edit data.js
2. Update the federalRates or provincialSupport objects
3. Re-deploy the updated file

## Security
- ✅ No external dependencies
- ✅ No data collection or storage
- ✅ All calculations performed client-side
- ✅ No vulnerabilities detected (CodeQL scan passed)

## Support
For questions or updates, refer to the main glbi.ca website.

## License
Based on public government data (PBO reports).
