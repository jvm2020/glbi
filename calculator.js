// GLBI Calculator Logic - Interactive Version

class GLBICalculator {
    constructor() {
        this.currentHouseholdType = 'single';
        this.initializeEventListeners();
        this.updateAllScenarios();
    }

    initializeEventListeners() {
        // Income slider
        const slider = document.getElementById('incomeSlider');
        const valueDisplay = document.getElementById('incomeValue');
        
        slider.addEventListener('input', (e) => {
            const value = parseInt(e.target.value);
            valueDisplay.textContent = value.toLocaleString();
            this.updateAllScenarios();
        });

        // Household type toggle
        const toggleBtns = document.querySelectorAll('.toggle-btn');
        toggleBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                toggleBtns.forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
                
                const type = btn.dataset.type;
                const householdOptions = document.querySelector('.household-options');
                
                if (type === 'household') {
                    householdOptions.style.display = 'block';
                    this.currentHouseholdType = document.getElementById('householdType').value;
                } else {
                    householdOptions.style.display = 'none';
                    this.currentHouseholdType = 'single';
                }
                
                this.updateAllScenarios();
            });
        });

        // Household type dropdown
        const householdSelect = document.getElementById('householdType');
        householdSelect.addEventListener('change', (e) => {
            this.currentHouseholdType = e.target.value;
            this.updateAllScenarios();
        });

        // Province change
        const provinceSelect = document.getElementById('province');
        provinceSelect.addEventListener('change', () => {
            this.updateAllScenarios();
        });

        // Disability checkbox
        const disabilityCheckbox = document.getElementById('hasDisability');
        disabilityCheckbox.addEventListener('change', () => {
            this.updateAllScenarios();
        });
    }

    updateAllScenarios() {
        const income = parseInt(document.getElementById('incomeSlider').value);
        const hasDisability = document.getElementById('hasDisability').checked;
        const province = document.getElementById('province').value;

        // Calculate for all three scenarios
        [50, 25, 15].forEach(rate => {
            const result = this.calculateGLBI(this.currentHouseholdType, income, rate, hasDisability);
            this.updateScenarioDisplay(rate, result, income);
        });

        // Update provincial comparison
        this.updateComparison(province, income);
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

    updateScenarioDisplay(rate, result, income) {
        document.getElementById(`total${rate}`).textContent = formatCurrency(result.totalIncome);
        document.getElementById(`glbi${rate}`).textContent = formatCurrency(result.glbiPayment);
        document.getElementById(`monthly${rate}`).textContent = formatCurrency(result.monthlyPayment);
    }

    updateComparison(province, income) {
        const provincialData = GLBI_DATA.provincialSupport[province];
        const currentSupport = provincialData[this.currentHouseholdType];
        
        // Use 25% scenario for comparison (middle ground)
        const hasDisability = document.getElementById('hasDisability').checked;
        const result = this.calculateGLBI(this.currentHouseholdType, income, 25, hasDisability);
        const difference = result.glbiPayment - currentSupport;
        
        const comparisonHTML = `
            <div class="comparison-item">
                <div>
                    <span class="comparison-label">GLBI Payment (25% scenario)</span>
                </div>
                <span class="comparison-value">${formatCurrency(result.glbiPayment)}</span>
            </div>
            
            <div class="comparison-item">
                <div>
                    <span class="comparison-label">Current ${provincialData.name} Support</span>
                </div>
                <span class="comparison-value">${formatCurrency(currentSupport)}</span>
            </div>
            
            <div class="comparison-item" style="background: ${difference >= 0 ? 'rgba(139, 0, 0, 0.2)' : 'rgba(220, 38, 38, 0.2)'}; border: 2px solid ${difference >= 0 ? 'var(--primary-color)' : '#dc2626'};">
                <div>
                    <span class="comparison-label">Difference</span>
                    <small style="display: block; color: var(--text-secondary); margin-top: 0.25rem;">
                        ${difference >= 0 ? 'GLBI provides MORE support' : 'Current system provides MORE support'}
                    </small>
                </div>
                <span class="comparison-value" style="color: ${difference >= 0 ? 'var(--primary-color)' : '#dc2626'}">
                    ${difference >= 0 ? '+' : ''}${formatCurrency(difference)}
                </span>
            </div>
            
            <div style="margin-top: 1.5rem; padding: 1rem; background: #1a1a1a; border-radius: 0.375rem;">
                <strong style="display: block; margin-bottom: 0.75rem; color: var(--text-primary);">Current programs in ${provincialData.name}:</strong>
                <ul style="margin: 0; padding-left: 1.5rem; font-size: 0.875rem; color: var(--text-secondary); line-height: 1.8;">
                    ${provincialData.programs.map(program => `<li>${program}</li>`).join('')}
                </ul>
            </div>
        `;
        
        document.getElementById('comparisonDetails').innerHTML = comparisonHTML;
    }
}

// Initialize calculator when DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new GLBICalculator();
});
