// GLBI Calculator Logic

class GLBICalculator {
    constructor() {
        this.initializeEventListeners();
    }

    initializeEventListeners() {
        const calculateBtn = document.getElementById('calculateBtn');
        calculateBtn.addEventListener('click', () => this.calculate());

        // Also calculate on Enter key in income field
        const incomeField = document.getElementById('annualIncome');
        incomeField.addEventListener('keypress', (e) => {
            if (e.key === 'Enter') {
                this.calculate();
            }
        });
    }

    calculate() {
        // Get input values
        const province = document.getElementById('province').value;
        const householdType = document.getElementById('householdType').value;
        const annualIncome = parseFloat(document.getElementById('annualIncome').value) || 0;
        const reductionRate = parseInt(document.getElementById('reductionRate').value);
        const hasDisability = document.getElementById('hasDisability').checked;

        // Calculate GLBI
        const result = this.calculateGLBI(householdType, annualIncome, reductionRate, hasDisability);
        
        // Display results
        this.displayResults(result, province, householdType, annualIncome, reductionRate, hasDisability);
    }

    calculateGLBI(householdType, annualIncome, reductionRate, hasDisability) {
        // Get base amount for household type
        const baseAmount = GLBI_DATA.federalRates[householdType];
        
        // Add disability amount if applicable (not clawed back)
        const disabilityAmount = hasDisability ? GLBI_DATA.disabilityAmount : 0;
        
        // Calculate reduction based on income earned
        const reductionPercentage = reductionRate / 100;
        const reduction = annualIncome * reductionPercentage;
        
        // Calculate GLBI payment (base - reduction + disability)
        // The disability amount is NOT reduced by income
        const glbiPayment = Math.max(0, baseAmount - reduction) + disabilityAmount;
        
        // Calculate total annual income
        const totalIncome = annualIncome + glbiPayment;
        
        // Calculate monthly payment
        const monthlyPayment = glbiPayment / 12;
        
        return {
            baseAmount,
            disabilityAmount,
            reduction,
            glbiPayment,
            totalIncome,
            monthlyPayment
        };
    }

    displayResults(result, province, householdType, annualIncome, reductionRate, hasDisability) {
        // Show results section
        const resultsSection = document.getElementById('resultsSection');
        resultsSection.style.display = 'block';
        
        // Scroll to results
        resultsSection.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
        
        // Update result values
        document.getElementById('totalIncome').textContent = formatCurrency(result.totalIncome);
        document.getElementById('glbiPayment').textContent = formatCurrency(result.glbiPayment);
        document.getElementById('monthlyPayment').textContent = formatCurrency(result.monthlyPayment);
        document.getElementById('baseAmount').textContent = formatCurrency(result.baseAmount);
        document.getElementById('reductionAmount').textContent = '-' + formatCurrency(result.reduction);
        
        // Show/hide disability amount
        const disabilityDisplay = document.getElementById('disabilityAmountDisplay');
        if (hasDisability) {
            disabilityDisplay.style.display = 'block';
            document.getElementById('disabilityAmount').textContent = formatCurrency(result.disabilityAmount);
        } else {
            disabilityDisplay.style.display = 'none';
        }
        
        // Update scenario description
        const scenarioDesc = GLBI_DATA.reductionRateDescriptions[reductionRate];
        document.getElementById('scenarioDescription').textContent = scenarioDesc;
        
        // Display comparison with provincial support
        this.displayComparison(province, householdType, result.glbiPayment, annualIncome);
    }

    displayComparison(province, householdType, glbiPayment, annualIncome) {
        const provincialData = GLBI_DATA.provincialSupport[province];
        const currentSupport = provincialData[householdType];
        const difference = glbiPayment - currentSupport;
        
        const comparisonHTML = `
            <div class="comparison-item">
                <div>
                    <span class="comparison-label">GLBI Annual Payment</span>
                    <span class="comparison-difference">Federal program</span>
                </div>
                <span class="comparison-value">${formatCurrency(glbiPayment)}</span>
            </div>
            
            <div class="comparison-item">
                <div>
                    <span class="comparison-label">Current ${provincialData.name} Support</span>
                    <span class="comparison-difference">Provincial + Federal programs</span>
                </div>
                <span class="comparison-value">${formatCurrency(currentSupport)}</span>
            </div>
            
            <div class="comparison-item" style="background: ${difference >= 0 ? '#f0fdf4' : '#fef2f2'}; border: 2px solid ${difference >= 0 ? '#059669' : '#dc2626'};">
                <div>
                    <span class="comparison-label">Difference</span>
                    <span class="comparison-difference ${difference >= 0 ? 'positive' : 'negative'}">
                        ${difference >= 0 ? 'GLBI provides MORE support' : 'Current system provides MORE support'}
                    </span>
                </div>
                <span class="comparison-value" style="color: ${difference >= 0 ? '#059669' : '#dc2626'}">
                    ${difference >= 0 ? '+' : ''}${formatCurrency(difference)}
                </span>
            </div>
            
            <div style="margin-top: 1rem; padding: 1rem; background: white; border-radius: 0.25rem; border: 1px solid var(--border-color);">
                <strong style="display: block; margin-bottom: 0.5rem;">Current programs in ${provincialData.name} include:</strong>
                <ul style="margin: 0; padding-left: 1.5rem; font-size: 0.875rem; color: var(--text-secondary);">
                    ${provincialData.programs.map(program => `<li>${program}</li>`).join('')}
                </ul>
            </div>
            
            ${annualIncome === 0 ? `
            <div style="margin-top: 1rem; padding: 1rem; background: #eff6ff; border-radius: 0.25rem; border-left: 4px solid var(--primary-color);">
                <p style="margin: 0; font-size: 0.875rem; color: var(--text-secondary);">
                    <strong>Note:</strong> This comparison shows the maximum benefit when you have no other income. 
                    As you earn income, GLBI payments are reduced based on the reduction rate scenario, 
                    but the disability amount (if applicable) remains constant.
                </p>
            </div>
            ` : ''}
        `;
        
        document.getElementById('comparisonDetails').innerHTML = comparisonHTML;
    }
}

// Initialize calculator when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new GLBICalculator();
});
